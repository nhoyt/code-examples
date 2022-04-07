import math
src = [2, 4, 9, 16, 25]

# standard approach
res = []
for x in src: res.append(math.sqrt(x))
print res

# map function to list elements
print map(math.sqrt, src)

# list comprehension
print [math.sqrt(x) for x in src]
