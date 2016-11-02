#import the library used to query a website
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup

#Create web browser to execute javascript so the menu can be loaded
browserC = webdriver.Chrome("/home/kyle/Desktop/textScraper/chromedriver")   
#specify the url
commons = 'https://calvin.edu/directory/places/commons-dining-hall'
knoll = 'https://calvin.edu/directory/places/knollcrest-dining-hall'

#get commons webpage
browserC.get(commons)
WebDriverWait(browserC, 10).until(
    EC.visibility_of_element_located((By.ID, "myMenuContainer")))
#save html of webpage after fully loading
pageC = browserC.page_source
#exit browser
browserC.quit()

browserK = webdriver.Chrome("/home/kyle/Desktop/textScraper/chromedriver") 
browserK.get(knoll)
WebDriverWait(browserK, 10).until(
   EC.visibility_of_element_located((By.ID, "myMenuContainer")))    
pageK = browserK.page_source
browserK.quit()

#Parse the html in the 'page' variable, and store it in Beautiful Soup format
soupC = BeautifulSoup(pageC, "html5lib")
soupK = BeautifulSoup(pageK, "html5lib")

#find the menu in the html
menuC = soupC.find('div', {'id':'myMenuContainer'})
menuK = soupK.find('div', {'id':'myMenuContainer'})

#format text to be useable and look nice
menuC = menuC.get_text()
menuC = menuC.replace("BreakfastHot", "Breakfast:\nHot")
menuC = menuC.replace("LunchSoup", "\n\n\nLunch: \nSoup")
menuC = menuC.replace("Flip-Side", "\nFlip-Side")
menuC = menuC.replace("Taqueria", "\nTaqueria")
menuC = menuC.replace("Piazza", "\nPiazza")
menuC = menuC.replace("UpperCrust", "\nUpperCrust")
menuC = menuC.replace("DinnerSoup", "\n\n\nDinner: \nSoup")

#format text to be useable and look nice, bad knollcrest edition 
k = ''
for content in menuK.contents:
     k += "\n" + str(content)

k = k.replace("<p>", "")
k = k.replace("</p>", "")
k = k.replace("<strong>", "")
k = k.replace("</strong>", "")
k = k.replace("<h3>", "")
k = k.replace("</h3>", ":")
k = k.replace("<br/>", "\n")
k = k.replace("<span style=\"text-decoration: underline;\">", "")
k = k.replace("</span>", ":")
k = k.replace("<hr/>", "")
k = k.replace("\nVegan: Soup:\n", "Vegan Soup:")


print menuC 
print "\n\n" 
print k

#write menus to txt file for app use
textC = open("commons.txt", "w")
textC.write(menuC.encode('utf-8'))
textC.close()

textK = open("knoll.txt", "w")
textK.write(k)
textK.close()
