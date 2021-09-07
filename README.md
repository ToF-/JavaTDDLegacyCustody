Our company sells a (unique) computation ressource as a service to clients in need of this computation. Our program receive competing rent order offers and computes the maximum income that can be made with these orders.

Each rent order includes
- the 8 character ID of the client
- the timestamp at which the rent should start (unix timestamp)
- the duration of the renting of our ressource
- the amount offered for the rent


Here's an example:

If you enter this data in the app,
```
1
4
TOPSTABS 0 5 100
DOESCLAN 3 7 140
MAZYCART 6 9 70
CODECOAT 7 9 80
```

Then the result will be:
```
180
```
Because
- TOPSTABS and DOESCLAN are not compatible, (TOPSTABS ends at 5, so DOESCLAN can't take place at 3).
- DOESCLAN is a better offer than TOPSTAPS at 140 instead of 100.
- but DOESCLAN, ending at 3+7=10 doesn't allow for MAZYCART or CODECOAT to start so total revenue would be only 140.
- MAZYCART can start right when TOPSTABS ends, so the total revenue could be 100+70: 170.
- CODECOAT can start after TOPSTABS ends and is better than MAZYCART at 80, so revenue can be 100+80: 180.

actual revenue is therefore: 180.

Of course in real life, we use our program on much larger data. Attached is a file from production (`largedata.dat`).

We would like you to change the code in 3 different ways:

- Improve maintainability of code. A consultant audited the code and declared it "unmaintainable", in particular, "it has no unit tests", and that is the reason that each time we tried to have this program evolve, incidents in production would result.

- Optimize the program. Currently it takes a little too long to deal with our large data sets. We would like it to process a large data set in say, less than half a second.

- Make the program display the trade orders that are chosen instead of only the revenue. For example, processing the file in the case above, the program would display the order id, the order amount and the best revenue so far.
```
TOPSTABS 100 100
CODECOAT  80 180
```
