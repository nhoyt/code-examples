import datetime

def age(bday, d=datetime.datetime.today()):
    return (d.year - bday.year) - \
           int((d.month, d.day) < (bday.month, bday.day))
