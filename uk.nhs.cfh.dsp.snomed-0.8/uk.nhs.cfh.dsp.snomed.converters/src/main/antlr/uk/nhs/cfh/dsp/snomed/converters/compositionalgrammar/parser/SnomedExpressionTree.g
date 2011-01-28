tree grammar SnomedExpressionTree;

options {

    // Default but name it anyway
    //
    language   = Java;

    // Use the vocab from the parser (not the lexer)
    // The ANTLR Maven plugin knows how to work out the
    // relationships between the .g files and it will build
    // the tree parser after the parser. It will also rebuild
    // the tree parser if the parser is rebuilt.
    //
    tokenVocab = SnomedExpression;

    // Use ANTLR built-in CommonTree for tree nodes
    //
    ASTLabelType = CommonTree;
}

// What package should the generated source exist in?
//
@header {

    package uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.parser;
    
    import java.util.Map;
    import java.util.TreeMap;
    import java.util.List;
    import uk.nhs.cfh.dsp.snomed.expression.model.*;
    import uk.nhs.cfh.dsp.snomed.expression.model.impl.*;
    import uk.nhs.cfh.dsp.snomed.objectmodel.*;
    import uk.nhs.cfh.dsp.snomed.objectmodel.impl.*;
    import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
    import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
    import org.apache.commons.logging.Log;
	import org.apache.commons.logging.LogFactory;
}

// We want to add some fields and methods to the generated class.
@members {

 	private Map<String, SnomedConcept> conceptMap = new TreeMap<String, SnomedConcept>();
 	private Map<PropertyExpression, Expression> propertyExpressionsMap = new TreeMap<PropertyExpression, Expression>();
  	private TerminologyConceptDAO terminologyConceptDAO; 
	private static Log logger = LogFactory.getLog(SnomedExpressionTree.class);
	private boolean getCloseToUserForm = false;

	public SnomedExpressionTree(CommonTreeNodeStream nodes, TerminologyConceptDAO terminologyConceptDAO){
		super(nodes);
		this.terminologyConceptDAO = terminologyConceptDAO;
	}

    private SnomedConcept checkAndGetConcept(String conceptId){
        if(conceptId != null)
        {
            SnomedConcept concept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, conceptId);
            if(concept != null)
            {
                return concept;
            }
            else
            {
                throw new IllegalArgumentException("Concept with given ID does not exist. Concept ID : "+conceptId);
            }
        }
        else
        {
            throw new IllegalArgumentException("Concept ID passed can not be null.");
        }
    }
    
    private SnomedRelationshipPropertyExpression getRelationshipExpression(SnomedConcept attribute, Expression value){

        if(attribute != null && value != null)
        {
            SnomedRelationshipPropertyExpression propertyExpression =
                                            new SnomedRelationshipPropertyExpression(attribute);
			// set expression value for propertyExpression
			propertyExpression.setExpression(value);
			// if expression has focus concepts, then set focus concept as targetConcept in property
			if(value instanceof ExpressionWithFocusConcepts)
			{
				ExpressionWithFocusConcepts aefc = (ExpressionWithFocusConcepts) value;
				SnomedConcept fc = aefc.getSingletonFocusConcept();
				propertyExpression.getRelationship().setTargetConcept(fc);
            	propertyExpression.getRelationship().setTargetConceptID(fc.getConceptID());
			}
			
            return propertyExpression;            
        }
        else
        {
            throw new IllegalArgumentException("Attribute and value passed can not be null.");
        }
    }
    
    private CloseToUserExpression getCloseToUserExpression(SnomedConcept concept){
    	return new CloseToUserExpressionImpl(concept);
    }
    
    private Expression getExpressionForTreeNode(CommonTree node){
    	String text = node.getText();
    	if(conceptMap.containsKey(text))
    	{
    		return getCloseToUserExpression(conceptMap.get(text)); 
    	}	
    	else
    	{
    		throw new RuntimeException("Concept Map does not contain concept");
    	}
    }
    
    private AbstractExpressionWithFocusConcepts getAbstractExpressionWithFocusConcepts(SnomedConcept concept){
    	if(isGetCloseToUserForm())
    	{
    		return new CloseToUserExpressionImpl(concept);
    	}
    	else
    	{
    	 	return new NormalFormExpressionImpl(concept);
    	}
    }
    
    private AbstractExpressionWithFocusConcepts getExpressionForTreeNode(String text){
    	if(conceptMap.containsKey(text))
    	{
    		return getAbstractExpressionWithFocusConcepts(conceptMap.get(text)); 
    	}	
    	else
    	{
    		throw new RuntimeException("Concept Map does not contain concept");
    	}
    }
    
    private SnomedRoleGroupExpression getRoleGroupExpression(List relationshipExpressions){
    	
    	SnomedRoleGroupExpression roleGroupExpression = new SnomedRoleGroupExpression(new SnomedRoleGroupImpl());
        for(int i=0; i<relationshipExpressions.size(); i++)
        {
            Object o = relationshipExpressions.get(i);
            if(o instanceof SnomedRelationshipPropertyExpression)
            {
                roleGroupExpression.addChildExpression((SnomedRelationshipPropertyExpression) o);
            }
        }
    	
    	return roleGroupExpression;
    }
    
    public boolean isGetCloseToUserForm() {
        return getCloseToUserForm;
    }

    public void setGetCloseToUserForm(boolean getCloseToUserForm) {
        this.getCloseToUserForm = getCloseToUserForm;
    }

} // @members


snomed	:	EXPRESSION+ ;
expression  returns [AbstractExpressionWithFocusConcepts exp]
@init{ if(isGetCloseToUserForm())
		{ exp = new CloseToUserExpressionImpl(); }
		else
		{ exp = new NormalFormExpressionImpl(); }
	}
	: ^(EXPRESSION (c=concept {$exp.addFocusConcept(c); })+ (r=refinements {$exp.setPropertyExpressions(r); })* )
	;
concept returns [SnomedConcept con] 
	: ^(CONCEPT (conId=conceptId {$con=checkAndGetConcept(conId); }) )
	;
conceptId returns [String conId]
@init{ conId = ""; }
	: ^(CONCEPT_ID (sctId=SCTID {$conId=$sctId.text; }) )
	;
refinements returns [List list]
@init{ list = new ArrayList(); }
	: ^(PROPERTY_EXPRESSIONS (set=attributeSet {$list.addAll(set); })? (group=attributeGroup {$list.add(group); })* )
	;
attributeGroup returns [SnomedRoleGroupExpression roleGroupExpression]
@init{ roleGroupExpression = new SnomedRoleGroupExpression(new SnomedRoleGroupImpl());}
	 : ^(ROLE_GROUP_EXPRESSION (v=attributeSet {$roleGroupExpression.setChildExpressions(v); })) 
	 ;
attributeSet returns [List list]
@init{ list = new ArrayList(); }
	: ^(RELATIONSHIP_EXPRESSION_COLLECTION (v=attribute {$list.add(v); })+ )
	;
attribute   returns [SnomedRelationshipPropertyExpression propertyExpression]
@init{propertyExpression = new SnomedRelationshipPropertyExpression();}
	: ^(RELATIONSHIP_EXPRESSION (name=attributeName {$propertyExpression.setRelationshipFromConcept(name); })
								(value=attributeValue {$propertyExpression.setExpressionAndPopulateFields(value); }) )
	;
attributeName  returns [SnomedConcept name]
	: ^(CONCEPT_ID (id=attributeNameId {$name=checkAndGetConcept($id.text); }) ) 
	;
attributeValue returns [AbstractExpressionWithFocusConcepts e]
 : ^(CONCEPT (con=concept { $e = getAbstractExpressionWithFocusConcepts(con); }) )
 | ^(EXPRESSION exp=expression {$e = (AbstractExpressionWithFocusConcepts) exp; }) ;

attributeNameId returns [String nameId]
	: ^(CONCEPT_ID (sctId=SCTID {$nameId = $sctId.text; }) )
	;
// always leave a blank line -- never leave the last line commented -- ANTLR gets itself in a twist!
