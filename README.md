## Munkres(Hangarian) Algorithm Implimentation for Scala

## Usage

```scala
import Munkres.*
val matrix =  Array(
    Array(5.0, 4.0, 7, 6.0),
    Array(6.0, 7.0, 3.0, 2),
    Array(8,11, 2.0, 5),
    Array(9, 8, 6.0, 7))
  )
minimize(m)
// => List((2,2), (0,1), (3,0), (1,3))
cost(m)
// 17
```

## TODO
- add padding
