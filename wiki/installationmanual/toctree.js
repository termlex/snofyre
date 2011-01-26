
      var tree;
      
      function treeInit() {
      tree = new YAHOO.widget.TreeView("treeDiv1");
      var root = tree.getRoot();
    

    
    
    var objd4e35 = { label: "Introduction", href:"topics/installation-manual-introduction.html", target:"contentwin" };
    var d4e35 = new YAHOO.widget.TextNode(objd4e35, root, false);var objd4e44 = { label: "Notice", href:"topics/snofyre-generic-notice.html", target:"contentwin" };
    var d4e44 = new YAHOO.widget.TextNode(objd4e44, d4e35, false);var objd4e53 = { label: "License", href:"topics/open-government-license.html", target:"contentwin" };
    var d4e53 = new YAHOO.widget.TextNode(objd4e53, d4e35, false);var objd4e62 = { label: "What is Snofyre?", href:"topics/snofyre-overview.html", target:"contentwin" };
    var d4e62 = new YAHOO.widget.TextNode(objd4e62, d4e35, false);
    var objd4e72 = { label: "Getting Started", href:"topics/configurator-getting-started.html", target:"contentwin" };
    var d4e72 = new YAHOO.widget.TextNode(objd4e72, root, false);var objd4e81 = { label: "System Requirements", href:"topics/systemRequirements.html", target:"contentwin" };
    var d4e81 = new YAHOO.widget.TextNode(objd4e81, d4e72, false);var objd4e90 = { label: "Reporting Issues", href:"tasks/reporting-issues.html", target:"contentwin" };
    var d4e90 = new YAHOO.widget.TextNode(objd4e90, d4e72, false);
    var objd4e100 = { label: "Snofyre Installer First Steps", href:"topics/snofyre-installation-first-steps.html", target:"contentwin" };
    var d4e100 = new YAHOO.widget.TextNode(objd4e100, root, false);var objd4e109 = { label: "Snofyre Installation Overview", href:"topics/snofyre-installation.html", target:"contentwin" };
    var d4e109 = new YAHOO.widget.TextNode(objd4e109, d4e100, false);var objd4e118 = { label: "Snofyre Installer Walkthrough", href:"tasks/snofyre-installer-walkthrough.html", target:"contentwin" };
    var d4e118 = new YAHOO.widget.TextNode(objd4e118, d4e100, false);
    var objd4e129 = { label: "Downloading and Installing MySQL Server", href:"topics/mysql-server-overview.html", target:"contentwin" };
    var d4e129 = new YAHOO.widget.TextNode(objd4e129, root, false);var objd4e138 = { label: "Downloading MySQL Server", href:"topics/downloading-mysql.html", target:"contentwin" };
    var d4e138 = new YAHOO.widget.TextNode(objd4e138, d4e129, false);var objd4e147 = { label: "Installing MySQL Server", href:"topics/installing-mysql.html", target:"contentwin" };
    var d4e147 = new YAHOO.widget.TextNode(objd4e147, d4e129, false);
    var objd4e157 = { label: "Installation and Configuration of MySQL Workbench", href:"topics/mysql-workbench-overview.html", target:"contentwin" };
    var d4e157 = new YAHOO.widget.TextNode(objd4e157, root, false);var objd4e166 = { label: "Downloading MySQL Workbench", href:"topics/downloading-mysql-workbench.html", target:"contentwin" };
    var d4e166 = new YAHOO.widget.TextNode(objd4e166, d4e157, false);var objd4e175 = { label: "Installing MySQL Workbench", href:"topics/installing-mysql-workbench.html", target:"contentwin" };
    var d4e175 = new YAHOO.widget.TextNode(objd4e175, d4e157, false);var objd4e184 = { label: "Starting up MySQL Workbench", href:"tasks/mysql-workbench-startup.html", target:"contentwin" };
    var d4e184 = new YAHOO.widget.TextNode(objd4e184, d4e157, false);var objd4e193 = { label: "Creating a New Connection", href:"tasks/mysql-workbench-newconnection.html", target:"contentwin" };
    var d4e193 = new YAHOO.widget.TextNode(objd4e193, d4e157, false);var objd4e202 = { label: "Creating a New Server Instance", href:"tasks/mysql-workbench-new-server-instance.html", target:"contentwin" };
    var d4e202 = new YAHOO.widget.TextNode(objd4e202, d4e157, false);
    var objd4e212 = { label: "Creating a MySQL User Account for Snofyre", href:"topics/mysql-workbench-account-creation-overview.html", target:"contentwin" };
    var d4e212 = new YAHOO.widget.TextNode(objd4e212, root, false);var objd4e221 = { label: "Creating a New User Account for Snofyre", href:"tasks/mysql-workbench-new-user-account.html", target:"contentwin" };
    var d4e221 = new YAHOO.widget.TextNode(objd4e221, d4e212, false);
    var objd4e231 = { label: "Importing Data into MySQL Server", href:"topics/mysql-workbench-importing-overview.html", target:"contentwin" };
    var d4e231 = new YAHOO.widget.TextNode(objd4e231, root, false);var objd4e240 = { label: "Importing Snofyre Data", href:"tasks/mysql-workbench-importing-snofyre-data.html", target:"contentwin" };
    var d4e240 = new YAHOO.widget.TextNode(objd4e240, d4e231, false);var objd4e249 = { label: "Importing SNOMED CT Data", href:"tasks/mysql-workbench-importing-snomedct-data.html", target:"contentwin" };
    var d4e249 = new YAHOO.widget.TextNode(objd4e249, d4e231, false);
    var objd4e259 = { label: "Settings permissions for Snofyre Account", href:"topics/mysql-workbench-account-permissions-overview.html", target:"contentwin" };
    var d4e259 = new YAHOO.widget.TextNode(objd4e259, root, false);var objd4e268 = { label: "Setting Permissions for Snofyre Data", href:"tasks/mysql-workbench-snofyre-db-permissions.html", target:"contentwin" };
    var d4e268 = new YAHOO.widget.TextNode(objd4e268, d4e259, false);var objd4e277 = { label: "Setting Permissions for SNOMED CT Data", href:"tasks/mysql-workbench-snomed-db-permissions.html", target:"contentwin" };
    var d4e277 = new YAHOO.widget.TextNode(objd4e277, d4e259, false);
    var objd4e287 = { label: "Snofyre Installer Closing Steps", href:"topics/snofyre-installer-closing-steps.html", target:"contentwin" };
    var d4e287 = new YAHOO.widget.TextNode(objd4e287, root, false);var objd4e296 = { label: "Confirming Configuration in Installer", href:"tasks/snofyre-installer-confirm-configuration.html", target:"contentwin" };
    var d4e296 = new YAHOO.widget.TextNode(objd4e296, d4e287, false);var objd4e305 = { label: "Confirming Database Credentials in Installer", href:"tasks/snofyre-installer-confirm-db-credentials.html", target:"contentwin" };
    var d4e305 = new YAHOO.widget.TextNode(objd4e305, d4e287, false);var objd4e314 = { label: "Shortcuts and Finish Panel", href:"tasks/snofyre-installer-shortcuts-finish-page.html", target:"contentwin" };
    var d4e314 = new YAHOO.widget.TextNode(objd4e314, d4e287, false);
    var objd4e325 = { label: "Running Snofyre", href:"topics/snofyre-getting-started.html", target:"contentwin" };
    var d4e325 = new YAHOO.widget.TextNode(objd4e325, root, false);var objd4e334 = { label: "Snofyre Quick Start", href:"tasks/snofyre-quick-start.html", target:"contentwin" };
    var d4e334 = new YAHOO.widget.TextNode(objd4e334, d4e325, false);

      tree.draw(); 
      } 
      
      YAHOO.util.Event.addListener(window, "load", treeInit); 
    