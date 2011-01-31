
      var tree;
      
      function treeInit() {
      tree = new YAHOO.widget.TreeView("treeDiv1");
      var root = tree.getRoot();
    

    
    
    
    var objd4e53 = { label: "How this document is organised", href:"topics/documentOrganisation.html", target:"contentwin" };
    var d4e53 = new YAHOO.widget.TextNode(objd4e53, root, false);
    var objd4e79 = { label: "Introduction", href:"topics/technical-architecture-manual-introduction.html", target:"contentwin" };
    var d4e79 = new YAHOO.widget.TextNode(objd4e79, root, false);var objd4e104 = { label: "Notice", href:"topics/snofyre-generic-notice.html", target:"contentwin" };
    var d4e104 = new YAHOO.widget.TextNode(objd4e104, d4e79, false);var objd4e129 = { label: "License", href:"topics/open-government-license.html", target:"contentwin" };
    var d4e129 = new YAHOO.widget.TextNode(objd4e129, d4e79, false);var objd4e154 = { label: "What is Snofyre?", href:"topics/snofyre-overview.html", target:"contentwin" };
    var d4e154 = new YAHOO.widget.TextNode(objd4e154, d4e79, false);
    var objd4e180 = { label: "Getting Started", href:"topics/user-manual-getting-started.html", target:"contentwin" };
    var d4e180 = new YAHOO.widget.TextNode(objd4e180, root, false);var objd4e205 = { label: "Purpose of this document", href:"topics/purposeOfDocument.html", target:"contentwin" };
    var d4e205 = new YAHOO.widget.TextNode(objd4e205, d4e180, false);var objd4e230 = { label: "Audience", href:"topics/audience.html", target:"contentwin" };
    var d4e230 = new YAHOO.widget.TextNode(objd4e230, d4e180, false);var objd4e255 = { label: "System Requirements", href:"topics/systemRequirements.html", target:"contentwin" };
    var d4e255 = new YAHOO.widget.TextNode(objd4e255, d4e180, false);var objd4e280 = { label: "Reporting Issues", href:"tasks/reporting-issues.html", target:"contentwin" };
    var d4e280 = new YAHOO.widget.TextNode(objd4e280, d4e180, false);
    var objd4e307 = { label: "Architecture Overview", href:"topics/application-framework-introduction.html", target:"contentwin" };
    var d4e307 = new YAHOO.widget.TextNode(objd4e307, root, false);var objd4e332 = { label: "Snofyre Application Framework Overview", href:"topics/application-framework-overview.html", target:"contentwin" };
    var d4e332 = new YAHOO.widget.TextNode(objd4e332, d4e307, false);var objd4e357 = { label: "High Level Application Framework", href:"topics/high-level-application-framework.html", target:"contentwin" };
    var d4e357 = new YAHOO.widget.TextNode(objd4e357, d4e307, false);var objd4e382 = { label: "Modular Docking Framework", href:"concepts/modular-docking-framework.html", target:"contentwin" };
    var d4e382 = new YAHOO.widget.TextNode(objd4e382, d4e357, false);var objd4e407 = { label: "View Component", href:"concepts/view-component.html", target:"contentwin" };
    var d4e407 = new YAHOO.widget.TextNode(objd4e407, d4e357, false);var objd4e432 = { label: "Action Component", href:"concepts/action-component.html", target:"contentwin" };
    var d4e432 = new YAHOO.widget.TextNode(objd4e432, d4e357, false);var objd4e457 = { label: "Application Listener", href:"concepts/application-listeners.html", target:"contentwin" };
    var d4e457 = new YAHOO.widget.TextNode(objd4e457, d4e357, false);var objd4e482 = { label: "Snofyre Generic Architecture Principles", href:"topics/snofyre-generic-architecture-principles.html", target:"contentwin" };
    var d4e482 = new YAHOO.widget.TextNode(objd4e482, d4e307, false);var objd4e507 = { label: "Snofyre External Frameworks", href:"topics/snofyre-external-frameworks.html", target:"contentwin" };
    var d4e507 = new YAHOO.widget.TextNode(objd4e507, d4e307, false);
    var objd4e533 = { label: "Services Overview", href:"topics/snofyre-core-services-overview.html", target:"contentwin" };
    var d4e533 = new YAHOO.widget.TextNode(objd4e533, root, false);var objd4e558 = { label: "SNOMED CT Modules and Services", href:"topics/snomed-services-overview.html", target:"contentwin" };
    var d4e558 = new YAHOO.widget.TextNode(objd4e558, d4e533, false);var objd4e583 = { label: "Snofyre Query Modules and Services", href:"topics/query-services-overview.html", target:"contentwin" };
    var d4e583 = new YAHOO.widget.TextNode(objd4e583, d4e533, false);var objd4e608 = { label: "Search Services and Modules", href:"topics/search-services-overview.html", target:"contentwin" };
    var d4e608 = new YAHOO.widget.TextNode(objd4e608, d4e533, false);
    var objd4e634 = { label: "Process Overview", href:"topics/snofyre-process-overview.html", target:"contentwin" };
    var d4e634 = new YAHOO.widget.TextNode(objd4e634, root, false);var objd4e659 = { label: "Data Flow in Snofyre", href:"topics/snofyre-data-flow.html", target:"contentwin" };
    var d4e659 = new YAHOO.widget.TextNode(objd4e659, d4e634, false);var objd4e684 = { label: "Query Execution Process", href:"topics/query-execution-process.html", target:"contentwin" };
    var d4e684 = new YAHOO.widget.TextNode(objd4e684, d4e634, false);var objd4e709 = { label: "Query Execution Considerations", href:"topics/query-execution-process-considerations.html", target:"contentwin" };
    var d4e709 = new YAHOO.widget.TextNode(objd4e709, d4e634, false);var objd4e734 = { label: "Database Dialect", href:"concepts/databaseDialect.html", target:"contentwin" };
    var d4e734 = new YAHOO.widget.TextNode(objd4e734, d4e709, false);var objd4e759 = { label: "Table Creation Strategy", href:"concepts/tableCreationStrategy.html", target:"contentwin" };
    var d4e759 = new YAHOO.widget.TextNode(objd4e759, d4e709, false);var objd4e784 = { label: "Query Execution State", href:"concepts/queryExecutionState.html", target:"contentwin" };
    var d4e784 = new YAHOO.widget.TextNode(objd4e784, d4e709, false);var objd4e809 = { label: "Data Generation Process", href:"topics/data-generation-process.html", target:"contentwin" };
    var d4e809 = new YAHOO.widget.TextNode(objd4e809, d4e634, false);var objd4e834 = { label: "Data Generation Considerations", href:"topics/data-generation-process-considerations.html", target:"contentwin" };
    var d4e834 = new YAHOO.widget.TextNode(objd4e834, d4e634, false);var objd4e859 = { label: "Data Generation Strategy", href:"concepts/data-generation-strategy.html", target:"contentwin" };
    var d4e859 = new YAHOO.widget.TextNode(objd4e859, d4e834, false);var objd4e884 = { label: "Interpreting Results", href:"topics/interpretingResults.html", target:"contentwin" };
    var d4e884 = new YAHOO.widget.TextNode(objd4e884, d4e634, false);var objd4e909 = { label: "Explaining Results", href:"concepts/explainingResults.html", target:"contentwin" };
    var d4e909 = new YAHOO.widget.TextNode(objd4e909, d4e634, false);
    var objd4e935 = { label: "Design & Modeling Overview", href:"topics/snofyre-model-architecture-overview.html", target:"contentwin" };
    var d4e935 = new YAHOO.widget.TextNode(objd4e935, root, false);var objd4e960 = { label: "Query Model", href:"topics/queryModel.html", target:"contentwin" };
    var d4e960 = new YAHOO.widget.TextNode(objd4e960, d4e935, false);var objd4e985 = { label: "Query Criterion", href:"concepts/query-criterion.html", target:"contentwin" };
    var d4e985 = new YAHOO.widget.TextNode(objd4e985, d4e960, false);var objd4e1010 = { label: "Query Component Expression", href:"concepts/query-component-expression.html", target:"contentwin" };
    var d4e1010 = new YAHOO.widget.TextNode(objd4e1010, d4e960, false);var objd4e1035 = { label: "Union Expression", href:"concepts/unionExpression.html", target:"contentwin" };
    var d4e1035 = new YAHOO.widget.TextNode(objd4e1035, d4e960, false);var objd4e1060 = { label: "Intersection Expression", href:"concepts/intersectionExpression.html", target:"contentwin" };
    var d4e1060 = new YAHOO.widget.TextNode(objd4e1060, d4e960, false);var objd4e1085 = { label: "Additional Constraint", href:"concepts/additionalConstraint.html", target:"contentwin" };
    var d4e1085 = new YAHOO.widget.TextNode(objd4e1085, d4e960, false);var objd4e1110 = { label: "Query Expression", href:"concepts/query-expression.html", target:"contentwin" };
    var d4e1110 = new YAHOO.widget.TextNode(objd4e1110, d4e960, false);var objd4e1135 = { label: "Query Statement", href:"concepts/queryStatement.html", target:"contentwin" };
    var d4e1135 = new YAHOO.widget.TextNode(objd4e1135, d4e960, false);var objd4e1160 = { label: "Query Entities and Relationships", href:"topics/entityRelationships.html", target:"contentwin" };
    var d4e1160 = new YAHOO.widget.TextNode(objd4e1160, d4e960, false);var objd4e1185 = { label: "Information Model", href:"topics/informationModel.html", target:"contentwin" };
    var d4e1185 = new YAHOO.widget.TextNode(objd4e1185, d4e935, false);var objd4e1210 = { label: "Clinical Entry", href:"concepts/clinicalEntry.html", target:"contentwin" };
    var d4e1210 = new YAHOO.widget.TextNode(objd4e1210, d4e1185, false);var objd4e1235 = { label: "Clinical Event", href:"concepts/clinicalEvent.html", target:"contentwin" };
    var d4e1235 = new YAHOO.widget.TextNode(objd4e1235, d4e1185, false);var objd4e1260 = { label: "Clinical Finding Entity", href:"concepts/clinicalFinding.html", target:"contentwin" };
    var d4e1260 = new YAHOO.widget.TextNode(objd4e1260, d4e1185, false);var objd4e1285 = { label: "Clinical Observation Entity", href:"concepts/clinicalObservation.html", target:"contentwin" };
    var d4e1285 = new YAHOO.widget.TextNode(objd4e1285, d4e1185, false);var objd4e1310 = { label: "Intervention Entity", href:"concepts/intervention.html", target:"contentwin" };
    var d4e1310 = new YAHOO.widget.TextNode(objd4e1310, d4e1185, false);var objd4e1335 = { label: "Anatomical Location Entity", href:"concepts/anatomicalLocation.html", target:"contentwin" };
    var d4e1335 = new YAHOO.widget.TextNode(objd4e1335, d4e1185, false);var objd4e1360 = { label: "Investigation Entity", href:"concepts/investigation.html", target:"contentwin" };
    var d4e1360 = new YAHOO.widget.TextNode(objd4e1360, d4e1185, false);var objd4e1385 = { label: "Medication Entity", href:"concepts/medication.html", target:"contentwin" };
    var d4e1385 = new YAHOO.widget.TextNode(objd4e1385, d4e1185, false);var objd4e1410 = { label: "Information Model Entity Relationships", href:"topics/informationModelEntityRelationships.html", target:"contentwin" };
    var d4e1410 = new YAHOO.widget.TextNode(objd4e1410, d4e1185, false);var objd4e1435 = { label: "Database Schema Designs", href:"topics/databaseSchemaDesign.html", target:"contentwin" };
    var d4e1435 = new YAHOO.widget.TextNode(objd4e1435, d4e935, false);var objd4e1460 = { label: "Table per Class Mapping", href:"concepts/tablePerClass.html", target:"contentwin" };
    var d4e1460 = new YAHOO.widget.TextNode(objd4e1460, d4e1435, false);var objd4e1485 = { label: "Table Per Class Hierarchy Mapping", href:"concepts/tablePerClassHierarchy.html", target:"contentwin" };
    var d4e1485 = new YAHOO.widget.TextNode(objd4e1485, d4e1435, false);var objd4e1510 = { label: "Table per Sub Class Mapping", href:"concepts/tablePerSubClass.html", target:"contentwin" };
    var d4e1510 = new YAHOO.widget.TextNode(objd4e1510, d4e1435, false);var objd4e1535 = { label: "Design Decisions Summary", href:"topics/designDecisionsSummary.html", target:"contentwin" };
    var d4e1535 = new YAHOO.widget.TextNode(objd4e1535, d4e935, false);

    var objd4e1561 = { label: "Appendix", href:"appendix/appendix.html", target:"contentwin" };
    var d4e1561 = new YAHOO.widget.TextNode(objd4e1561, root, false);var objd4e1586 = { label: "Query Statement Examples", href:"appendix/queryStatementExamples.html", target:"contentwin" };
    var d4e1586 = new YAHOO.widget.TextNode(objd4e1586, d4e1561, false);var objd4e1611 = { label: "Data Generation Panel", href:"appendix/data-generation-panel.html", target:"contentwin" };
    var d4e1611 = new YAHOO.widget.TextNode(objd4e1611, d4e1561, false);var objd4e1636 = { label: "Query Authoring Panel", href:"appendix/queryCreationPanel.html", target:"contentwin" };
    var d4e1636 = new YAHOO.widget.TextNode(objd4e1636, d4e1561, false);var objd4e1661 = { label: "Query Results Panel", href:"appendix/resultViewPanel.html", target:"contentwin" };
    var d4e1661 = new YAHOO.widget.TextNode(objd4e1661, d4e1561, false);

      tree.draw(); 
      } 
      
      YAHOO.util.Event.addListener(window, "load", treeInit); 
    