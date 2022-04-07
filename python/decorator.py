#!/usr/bin/env python

from time import ctime, sleep

def timestamp(func):
    def wrapper():
        print '[%s] %s() called' % (ctime(), func.__name__)
        return func()
    return wrapper

@timestamp
def foo():
    pass

foo()
sleep(4)

for i in range(2):
    sleep(1)
    foo()
