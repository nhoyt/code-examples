import re

pattern = '\$[0-9]{1,3}\.[0-9]{2}'

f = open('books.txt', 'r')

for line in f:
    if re.search(pattern, line):
        lst = re.split('\$', line)
        print lst[1],
#       print line,

f.close()
