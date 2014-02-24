from datetime import *
from twython import *
from twitterdata import *
from time import time
import time
import math

sondermenu_url = "http://www.uk-sh.de/uksh_media/Speisepl%C3%A4ne/L%C3%BCbeck+_+Sondermen%C3%BC+/Sondermen%C3%BC+KW+";

#Week 1
t_11="1. Schinkennudeln + Tomatensauce; 2. Kartoffelauflauf, Schnittlauchsauce, Broccoli. Suppe: Gemüse";
t_12="1. Seelachsfilet, Dillsauce, Blattspinat, Kartoffeln. 2. Champignons, Spagetti, Käsesahnesauce. Suppe: Kräuter";
t_13="1. Schweinabraten, Rotkohl, Kartoffel; 2. Blumenkohl-Käsebratling. Kartoffeln. Kräutersauce. Suppe: Broccoli";
t_14="1. Rindergeschnetzeltes, Champignons, Spätzle; 2. Fagottini, Gemüsestreifen, Broccoli, Tomatensauce. Suppe: Champ.";
t_15="1. Putenbraten, Rustika-Gemüse; 2. Milchreis mit Kirschsauce. Suppe: Kartoffelsuppe.";
#Week 2
t_21="1. Tortellini mit Rind und Paprikastreifen; 2. Sellerie-Knusperschnitzel mit Reis & Tomatensauce. Suppe: Möhren.";
t_22="1. Hackbraten vom Rind mit Bohnen; 2. Saccotini Rucola mit Blattspinat. Tagessuppe. Blumenkohl";
t_23="1. Hähnchenbrustfiletspieß & Champignonsauce mit Gemüse; 2. Grießbrei mit Fruchtsauce. Tagessuppe: Gemüse";
t_24="1. Sauerbraten, Rosinensauce, Rotkohl, Kartoffelklöße; 2. Buonfatti Mediterran buntes Gemüse, Tomatensauce. Suppe: Spinat";
t_25="1. ged. Fischfilet, Senfsauce, Petersilienkartoffeln; 2. Maultaschen mit Spinat im Gemüsesud. Tagessuppe: Spargel";
#Week 3
t_31="1. Hähnchenbrustfilet, S. Hollandaise, Romanogemüse; 2. Kartoffeltaschen mit Frischkäse, Broccoli. Suppe: Gemüse.";
t_32="1. Königsberger Klopse, Kapernsauce, Petersilienkart. 2. Vegetarisches Chili";
t_33="1. Bolognese Gabelspaghetti; 2. Veg. Bratwurst, Erbsen-Maisgemüse in Rahm, Kartoffelpü. Suppe: Broccoli";
t_34="1. Geflügelfrikadelle, Kohlrabi in Rahm, Petersilienkartoffeln. 2. Frühlingsrolle mit Reis. Tagessuppe: Champignon";
t_35="1. Bratwurst, Majoransauce, Erbsen-Karotten-Püree; 2. Spätzle-Gemüsepfanne mit Käsesauce. Suppe: Kartoffel";
#Week 4 (also Week 0)
t_01="1. Hähnchenbrustfilet, Kaisergemüse, Petersilienkart.; 2. Vollkorn-Pilzbratling, Zitronensauc. Gemüsereis. Suppe: Möhren.";
t_02="1. Fischfilet, Remouladensauce, Majonäse-Kartoffelsalat; 2. Spagetti Napoli mit Parmesan. Suppe: Blumenkohl.";
t_03="1. Paniertes Schweineschnitzel mit Nudeln; 2. Spinat-Omlett mir Zwiebelsauce, Röstkartoffeln. Suppe: Gemüse";
t_04="1. Gulasch vom Schwein, Erbsen, Spirelli; 2. Gnocci mit Tomaten- Paprikasauce. Suppe: Spinat";
t_05="1. Hackbällchen, Pilzrahmsauce, Gemüsereis, Kartoffeln; 2. Champignon-Blätterteig-Tasche. Suppe: Spargel.";

foodDictionary = {"11":t_11, "12":t_12, "13":t_13, "14":t_14, "15":t_15, "21": t_21, "22":t_22,
                  "23":t_23, "24":t_24, "25":t_25, "31":t_31, "32":t_32, "33":t_33, "34":t_34,
                  "35":t_35, "01":t_01, "02":t_02, "03":t_03, "04":t_04, "05":t_05}

number_of_weeks=4

#tweets any text give to it to the preconfigured twitter account
def tweet(text):
    print(text)
    twitter = Twython(oauth_consumer_key, oauth_consumer_secret, oauth_access_token,
                      oauth_access_token_secret)
    #twitter.update_status(status=text)

#gets the number of the week ranging from 0 to numberofweeks-1
def getWeekNumber(d):
    now = datetime(d.tm_year,d.tm_mon,d.tm_mday)
    start = datetime(2012, 9, 28) #this was the first week with the new food-plan
    dif = now-start
    difdays = dif.days
    difdays=math.floor(difdays/7)+1
    weeknumber = difdays%number_of_weeks;
    return weeknumber

#gets the calenderweek, which is the number of weeks since the year begun
def kw(d):
    dt = datetime(d.tm_year,d.tm_mon,d.tm_mday)
    weekoftheyear = dt.isocalendar()[1]
    return weekoftheyear

def minuteofthehour(d):
    return d.tm_min

def houroftheday(d):
    return d.tm_hour

def dayoftheweek(d):
    return d.tm_wday+1

def getTodaysMeal(d):
    return foodDictionary[""+str(getWeekNumber(d))+str(dayoftheweek(d))]

def getTommorowsMeal(d):
    day = dayoftheweek(d)
    week= getWeekNumber(d);
    #if checked on sunday we have to set day to 1 and go to the next week
    if day==7:
        day=1
        week=week+1
        if week==number_of_weeks:
            week=0
    return foodDictionary[""+str(week)+str(day)]

def isWeekEnd(d):
    if dayoftheweek(d)==6 or dayoftheweek(d)==7:
        return 1
    return 0
    
def tommorowIsWeekEnd(d):
    if dayoftheweek(d)==5:
        return 1
    return 0

def isHollyday(d):
   #german reunification day
   if d.tm_mday==3 and d.tm_mon==10:
       return 1
   #christmas
   if d.tm_mday==24 and d.tm_mon==12:
        return 1
   #more christmas
   if d.tm_mday==25 and d.tm_mon==12:
        return 1
   #still christmas
   if d.tm_mday==26 and d.tm_mon==12:
        return 1
   #new years eve
   if d.tm_mday==31 and d.tm_mon==12:
        return 1
   #new year
   if d.tm_mday==1 and d.tm_mon==1:
        return 1
   #german labour day
   if d.tm_mday==1 and d.tm_mon==5:
        return 1
   return 0

#very unelegant...
def daybeforehollyday(d):
    #get the epoch from current date object
    x = time.mktime(d);
    #add 86400 seconds (one day)
    x=x+86400
    #create a struct time object from that
    d2 = time.localtime(x)
    #check if that date is a weekend
    return isHollyday(d2)

def sondermenue(d):
    return "Sondermenü: "+sondermenu_url+str(kw(d))+".pdf"

def mainfunction(d):
    #april fools joke
    if d.tm_mon==4 and d.tm_mday==1:
        if houroftheday(d)==7 and minuteofthehour(d)==30: #its 7:30 in the morning
            tweet("Heute kocht Jamie Oliver nur für @Flasher1984!")
            tweet("Sondermenü: http://www.youtube.com/watch?v=oHg5SJYRHA0")
    #happy holydays
    if d.tm_mon==12 and d.tm_mday==20:
        if houroftheday(d)==13 and minuteofthehour(d)==0:
            tweet("Ich bin raus. Happy Hollidays Bitches.")
    #actual checks
    if not(isHollyday(d)) and not(isWeekEnd(d)):
        #print("have to do stuff")
        if houroftheday(d)==7 and minuteofthehour(d)==30:
            tweet("Heute "+getTodaysMeal(d))
            tweet(sondermenue())
        if houroftheday(d)==11 and minuteofthehour(d)==0:
            tweet("11am Update "+getTodaysMeal(d))
            tweet(sondermenue(d))
    if not(daybeforehollyday(d)) and not(tommorowIsWeekEnd(d)):
        if houroftheday(d)==17 and minuteofthehour(d)==0:
           tweet("Morgen "+getTommorowsMeal(d))

while 1:
    #Get local time
    d = time.localtime()
    #Call the main fuction
    mainfunction(d)    
    #sleep for about 1 minute
    time.sleep(59.555)
