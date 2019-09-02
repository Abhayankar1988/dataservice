# dataservice

Requirements
A new market data service is needed which is able to handle a file input of prices associated with instruments. This
data needs to be persisted to an in memory database then a report produced at a specified period of time.
Your task is to implement a price processing service. A writer class should be able to populate the service periodically
with dummy data from the price source. Running in parallel is a reader class that should be able to get the data from
the service and store it in an in memory database. You should be using hibernate or spring jdbc for data access and
HSQLDB for persistence, plus any additional classes you may need. After 30 seconds, the program should exit and a
report should be generated that shows the second highest persisted price for each instrument over the entire
period, and the average persisted price over the last 10 seconds. The report can be on the command line or a file.
The sample data file is enclosed and contains test instruments and their prices. This file should be read continuously
in a loop as it only contains enough data for 10 seconds. It is split into groups of 4 instruments. One group should be
read in by the application every second. GOOG and BP.L should be persisted in the in memory DB every 3 seconds.
BT.L and VOD.L should be persisted every 5 seconds. Your application should be initialised with this information and
you need to ensure that the data in the service is always up to date by polling the data source at the right
frequency. It should output to the command line in a similar manner to the attached example output.
You solution should be multithreaded – i.e. the writer and reader class/es should be running on separate threads.
Your solution should be smart enough to make sure that no data are orphaned in the service (all data are persisted
to the in memory and are analysed by the report).
No Thread.sleep should be seen in the source code of the solution.
Solution
Your solution should provide:
• All of the source code
• All unit tests
• Documentation of any assumptions made
• Maven should be used for dependency management
• A zip file containing the working jar and any required libraries
• Evidence that all components work correctly
• Output from running your application
o Every second there should be output showing what has been read in from the file
o Each time an instrument is persisted in the database
o The reporting requirements when the program finishes detailed above
• We should be able to compile and run the application along with any necessary tests
• An appropriate logging framework should be used
• The only external libraries used should be Spring, Hibernate (if used), and HSQLDB. If there is a strong use for
another library this should be explained.


#Steps to run the application
run "mvn spring-boot:run"
