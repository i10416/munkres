# example

```scala mdoc:invisible
import dev.i10416.munkres.*
```

```scala mdoc
val matrix =   Array(
      Array(5.0, 4.0, 7, 6.0),
      Array(6.0, 7.0, 3.0, 2),
      Array(8, 11, 2.0, 5),
      Array(9, 8, 6.0, 7)
    )

///  returns combination which minimize total cost
Munkres.minimize(matrix)
///  returns cost
Munkres.cost(matrix)
```
