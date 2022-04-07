import locale

mystr = '123456'
mynum = int(mystr)
locale.setlocale(locale.LC_ALL, "")
print locale.format('%.0f', mynum, 3)
