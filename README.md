# Core-Test-utils-Discovery-Take-Home-Assessment
This library lets you use as core testing java-selenium utils for the web application assignment.
I have written this library to add all the reusable java classes. It has a maven parent and child project model. This approach enable to add multiple modules to this project and create the snapshots and use in the test application projects, presently it is having two-child project by name driver utils and general utils, and you can add similarly DB utils, Auth Utils etc. projects.
Driver utils has reusable java necessary classes used the oops concepts and java page object module and singleton design patterns.

DriverInilializer: It instantiate the web driver once using single ton design pattern and used to launch any of the remote driver (chrome,Firefox,Browser Stack etc).

Page Object: it is an abstract class, used page object module design pattern and loadable component class to make sure the AUT web elements loaded. It has java wrapper methods to utilize to perform the operations on webpages.

PropertFileReader: to read configuration file added in the test application project
Time Entity and Time Manager: enum and a reusable class.


To use this library, run maven commands to generate each module and project snapshots to use in the test application to be automate and use it.
Add the snapshot in test application project pom.xml.


	 

