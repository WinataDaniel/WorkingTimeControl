# TimeSystem
The purpose of the system of time and attendance is to keep a register of employees and their working hours. In addition to recording the working hours of all employees in the company the system should also check these recorded working times to determine whether they comply with the corresponding normative provisions. The entry of working hours into the system must not violate the normative regulations. In the event of a violation, a meaningful error message is issued and the entry is not accepted in the system. Each entry is checked directly and if the normative provisions were no longer kept by the input, the input will not be accepted by the time and attendance system. At the beginning, the working time recording system does not contain any predefined employees or working hours, these are only generated via the interactive user interface. Only the dates of the public holidays to be taken into account are read in when the system is started. Core working time, vacation leave or pay slips are not considered in the working time recording system.

A1. Working time

Working time is a time from start until the end of a work without pauses. The working time of each worker should not intersect with each other and be at least 1 minute. The working time is recorded and checked in steps of one minute. The basis for this forms the Gregorian calendar in use today and the usual 24-hour counting. The most important points about the break in working hours are summarized again. Year: Consists of 12 ordered months and lasts either 365 days or 366 days in a leap year and starts on January 1st and ends on December 31st. In the time and attendance system only the years between 1000 and 9999 are considered.

Leap year: A year that can be divided by 400 without a remainder is a leap year and a year, which is divisible by 4 without a remainder, is also a leap year if this year is not is divisible by 100.

Calender year: Fixed month from the first to the last. The ordered order of the 12th Calendar months of the year are: January with 31 days, February with 28 days or in the leap year 29 days, March with 31 days, April with 30 days, May with 31 days, June with 30 days, July with 31 days, August with 31 days, September with 30 days, October with 31 days, November with 30 days and December with 31 days.

Day: Always consists of 24 hours of equal length. The time for the first hour ranges from 00:00 to 01:00, for the 24th hour from 23:00 to 24:00. The count is down midnight, but in the counting, the minute changes directly to the minute at 11:59 p.m. 00:00 the following day. Midnight is only allowed as the end time of a day.

Holiday: A day with a special holiday rest on which ordinary employees do not work.

Textual representaton: The textual representation of the numeric date formats corresponds to the YYYY-MM-DD form and the time information corresponds to the hh:mm form. If the two formats are combined, the textual representation corresponds to the YYYY-MM-DDThh:mm form. Single-digit values for month, day, hours and minutes are always followed by a 0 and 2 places are filled. The symbol - is used between date units and - between time units and the colon: used as a separator. The capital letter T is the separator of date and time. An example of the date is 2007-12-03 (December 3, 2007), for the Time 10:15 (10 a.m. and 15 minutes) and for both together 2007-12-03T10:15.

A2. Holidays

To read in the dates of the public holidays, the working time recording system the only command line argument opposes a path to a text file. In the working time recording system are only taken into account the public holidays defined there. The Text file contains one or more lines, each line describes a date, which is in the YYYY-MM-DD form. An exemplary correct text file could look like this:

1 1964-01-01 2 1964-01-20 3 1964-02-17 4 1964-03-29 5 1964-05-25 6 1964-07-04 7 1964-09-07 8 1964-10-12 9 1964-11-11 10 1964-11-26 11 1964-12-25

A3. Employees

Employees are workers and employees as well as those employed for their vocational training in the medium-sized company. Night workers are workers who can do night work. Night work is every work that lasts more than 2 hours of the night. Night time is the time from 23:00 to 6:00. 6:00 a.m. as the start time, 11 p.m. as the end time and rest breaks during the night time not counted as night work. Production workers are workers who are directly responsible for carrying out of the production process.

A4. Normative regulations

The daily working time of the employee may not exceed 8 hours. You can go up to 10 hours if within 6 calendar months or within 24 weeks on average 8 hours per day are not exceeded. There are no explicit normative provisions on monthly working hours, as these are implicit can be derived from the provisions on daily working hours.

A4.1 Pauses

The work is in breaks of at least 30 minutes with a working time of more than 6 to to interrupt a total of 9 hours and 45 minutes with a working time of more than 9 hours.

4.2 Night work

The daily working hours of night workers must not exceed 8 hours. You can on up only be extended to 10 hours if within one calendar month or within 4 weeks on average 8 hours per day are not exceeded. For periods when night workers are not used for night work, the daily working hours can only be extended up to 10 hours if within 6 calendar months or within an average of 8 hours per day should not be exceeded for a period of 24 weeks.

A4.3 Sundays and Holidays

Employees may not be employed on Sundays and public holidays from 0 to 24. Different of this, production workers may be employed on Sundays and public holidays to prevent interruption of the production process. At least 15 Sundays in year should be without going to work. The regulations apply to employment on Sundays and public holidays accordingly, i.e. the working hours on Sundays and public holidays allow certain maximum working hours and compensation periods are not exceeded.

The example interaction can be seen in repository.

Thanks for your attention!

Good luck y'all!
