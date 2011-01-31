
      var tree;
      
      function treeInit() {
      tree = new YAHOO.widget.TreeView("treeDiv1");
      var root = tree.getRoot();
    

    
    
    var objd4e53 = { label: "Introduction", href:"topics/user-manual-introduction.html", target:"contentwin" };
    var d4e53 = new YAHOO.widget.TextNode(objd4e53, root, false);var objd4e78 = { label: "Notice", href:"topics/snofyre-generic-notice.html", target:"contentwin" };
    var d4e78 = new YAHOO.widget.TextNode(objd4e78, d4e53, false);var objd4e103 = { label: "License", href:"topics/open-government-license.html", target:"contentwin" };
    var d4e103 = new YAHOO.widget.TextNode(objd4e103, d4e53, false);var objd4e128 = { label: "What is Snofyre?", href:"topics/snofyre-overview.html", target:"contentwin" };
    var d4e128 = new YAHOO.widget.TextNode(objd4e128, d4e53, false);
    var objd4e154 = { label: "Getting Started", href:"topics/user-manual-getting-started.html", target:"contentwin" };
    var d4e154 = new YAHOO.widget.TextNode(objd4e154, root, false);var objd4e179 = { label: "System Requirements", href:"topics/systemRequirements.html", target:"contentwin" };
    var d4e179 = new YAHOO.widget.TextNode(objd4e179, d4e154, false);var objd4e204 = { label: "Reporting Issues", href:"tasks/reporting-issues.html", target:"contentwin" };
    var d4e204 = new YAHOO.widget.TextNode(objd4e204, d4e154, false);
    var objd4e230 = { label: "Working with Queries", href:"topics/workingWithQueries.html", target:"contentwin" };
    var d4e230 = new YAHOO.widget.TextNode(objd4e230, root, false);var objd4e255 = { label: "Creating a Simple Query", href:"tasks/creating-simple-query.html", target:"contentwin" };
    var d4e255 = new YAHOO.widget.TextNode(objd4e255, d4e230, false);var objd4e280 = { label: "Working with Composite Queries", href:"topics/creating-composite-queries.html", target:"contentwin" };
    var d4e280 = new YAHOO.widget.TextNode(objd4e280, d4e230, false);var objd4e305 = { label: "Creating an OR Query", href:"tasks/creating-or-queries.html", target:"contentwin" };
    var d4e305 = new YAHOO.widget.TextNode(objd4e305, d4e280, false);var objd4e330 = { label: "Creating an AND Query", href:"tasks/creating-and-queries.html", target:"contentwin" };
    var d4e330 = new YAHOO.widget.TextNode(objd4e330, d4e280, false);var objd4e355 = { label: "Saving a Query", href:"tasks/savingQuery.html", target:"contentwin" };
    var d4e355 = new YAHOO.widget.TextNode(objd4e355, d4e230, false);var objd4e380 = { label: "Loading a Query", href:"tasks/loadingQuery.html", target:"contentwin" };
    var d4e380 = new YAHOO.widget.TextNode(objd4e380, d4e230, false);var objd4e405 = { label: "Executing a Query", href:"tasks/executingQuery.html", target:"contentwin" };
    var d4e405 = new YAHOO.widget.TextNode(objd4e405, d4e230, false);var objd4e430 = { label: "Changing Execution State", href:"tasks/changingExecutionState.html", target:"contentwin" };
    var d4e430 = new YAHOO.widget.TextNode(objd4e430, d4e405, false);var objd4e455 = { label: "Changing Subsumption Status", href:"tasks/toggleSubsumption.html", target:"contentwin" };
    var d4e455 = new YAHOO.widget.TextNode(objd4e455, d4e405, false);
    var objd4e482 = { label: "Working with Results", href:"topics/workingWithResults.html", target:"contentwin" };
    var d4e482 = new YAHOO.widget.TextNode(objd4e482, root, false);var objd4e507 = { label: "Viewing Results", href:"tasks/viewingResults.html", target:"contentwin" };
    var d4e507 = new YAHOO.widget.TextNode(objd4e507, d4e482, false);var objd4e532 = { label: "Exporting Results", href:"tasks/exportingResults.html", target:"contentwin" };
    var d4e532 = new YAHOO.widget.TextNode(objd4e532, d4e482, false);var objd4e557 = { label: "Explaining Results", href:"tasks/explainResults.html", target:"contentwin" };
    var d4e557 = new YAHOO.widget.TextNode(objd4e557, d4e482, false);
    var objd4e583 = { label: "Working with Data", href:"topics/workingWithData.html", target:"contentwin" };
    var d4e583 = new YAHOO.widget.TextNode(objd4e583, root, false);var objd4e608 = { label: "Generating Data", href:"tasks/snofyre-generating-data.html", target:"contentwin" };
    var d4e608 = new YAHOO.widget.TextNode(objd4e608, d4e583, false);var objd4e633 = { label: "Deleting All Records", href:"tasks/snofyre-deleting-all-records.html", target:"contentwin" };
    var d4e633 = new YAHOO.widget.TextNode(objd4e633, d4e583, false);
    var objd4e659 = { label: "Uninstalling Snofyre", href:"topics/uninstalling-snofyre-overview.html", target:"contentwin" };
    var d4e659 = new YAHOO.widget.TextNode(objd4e659, root, false);var objd4e684 = { label: "How to uninstall Snofyre", href:"tasks/snofyre-uninstallation-process.html", target:"contentwin" };
    var d4e684 = new YAHOO.widget.TextNode(objd4e684, d4e659, false);
    var objd4e710 = { label: "User Interface Elements", href:"appendix/userInterfaceElements.html", target:"contentwin" };
    var d4e710 = new YAHOO.widget.TextNode(objd4e710, root, false);var objd4e735 = { label: "Query Authoring Panel", href:"appendix/queryCreationPanel.html", target:"contentwin" };
    var d4e735 = new YAHOO.widget.TextNode(objd4e735, d4e710, false);var objd4e760 = { label: "Active Query Panel", href:"appendix/activeQueryPanel.html", target:"contentwin" };
    var d4e760 = new YAHOO.widget.TextNode(objd4e760, d4e710, false);var objd4e785 = { label: "Query Results Panel", href:"appendix/resultViewPanel.html", target:"contentwin" };
    var d4e785 = new YAHOO.widget.TextNode(objd4e785, d4e710, false);var objd4e810 = { label: "Results Explanation Panel", href:"appendix/results-explanation-panel.html", target:"contentwin" };
    var d4e810 = new YAHOO.widget.TextNode(objd4e810, d4e710, false);var objd4e835 = { label: "Data Generation Panel", href:"appendix/data-generation-panel.html", target:"contentwin" };
    var d4e835 = new YAHOO.widget.TextNode(objd4e835, d4e710, false);
    var objd4e861 = { label: "Appendix", href:"appendix/appendix.html", target:"contentwin" };
    var d4e861 = new YAHOO.widget.TextNode(objd4e861, root, false);var objd4e886 = { label: "Query Statement Examples", href:"appendix/queryStatementExamples.html", target:"contentwin" };
    var d4e886 = new YAHOO.widget.TextNode(objd4e886, d4e861, false);var objd4e911 = { label: "Docked Panel", href:"concepts/dockedPanel.html", target:"contentwin" };
    var d4e911 = new YAHOO.widget.TextNode(objd4e911, d4e861, false);var objd4e936 = { label: "Query Execution State", href:"concepts/queryExecutionState.html", target:"contentwin" };
    var d4e936 = new YAHOO.widget.TextNode(objd4e936, d4e861, false);var objd4e961 = { label: "Query Criterion", href:"concepts/query-criterion.html", target:"contentwin" };
    var d4e961 = new YAHOO.widget.TextNode(objd4e961, d4e861, false);var objd4e986 = { label: "Explaining Results", href:"concepts/explainingResults.html", target:"contentwin" };
    var d4e986 = new YAHOO.widget.TextNode(objd4e986, d4e861, false);var objd4e1011 = { label: "Data Generation Considerations", href:"topics/data-generation-process-considerations.html", target:"contentwin" };
    var d4e1011 = new YAHOO.widget.TextNode(objd4e1011, d4e861, false);

      tree.draw(); 
      } 
      
      YAHOO.util.Event.addListener(window, "load", treeInit); 
    