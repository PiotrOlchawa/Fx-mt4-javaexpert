# JavaMt4ExpertAdvisor

JavaMt4ExpertAdvisor is a Mt4 Trading Platform multi-currency real time java CSV log file interpreter.
This application connect to resource where files are stored, detect file changes, read, interpret and send possible trading signal allerts
via email. 

How does the search algorithm for potential signals work ?

1. Algorithm scans entries from a CSV file, interprets each entry as a candle and looks for the fundamental elements of trading on the chart of support and resistance lines.
 
2. Algorithm  collected support and resistance minor-types are evaluated for a breakthrough (support or resistance break).

3. Algorithm show results after scanning CSV File:

Example Outputs with S/R Types

![Alt text](ex_output.jpg?raw=true "Title")


Finally : Algorithm checks "signal finishing" qualifies and send email for the correct signal.

## Getting Started

### Prerequisites

To run application you need java 1.8, gradle

### Running

To run application type

```
gradle run
```


