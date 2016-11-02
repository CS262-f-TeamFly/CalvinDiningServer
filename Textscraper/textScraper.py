#import the library used to query a website
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
import json

browserC = webdriver.Chrome("/home/kyle/Desktop/textScraper/chromedriver")   
#specify the url
commons = 'https://calvin.edu/directory/places/commons-dining-hall'
knoll = 'https://calvin.edu/directory/places/knollcrest-dining-hall'

browserC.get(commons)
WebDriverWait(browserC, 10).until(
    EC.visibility_of_element_located((By.ID, "myMenuContainer")))
pageC = browserC.page_source
browserC.quit()

browserK = webdriver.Chrome("/home/kyle/Desktop/textScraper/chromedriver") 
browserK.get(knoll)
WebDriverWait(browserK, 10).until(
   EC.visibility_of_element_located((By.ID, "myMenuContainer")))    
pageK = browserK.page_source
browserK.quit()

#Query the website and return the html to the variable 'page'

#import the Beautiful soup functions to parse the data returned from the website


#Parse the html in the 'page' variable, and store it in Beautiful Soup format
soupC = BeautifulSoup(pageC)
soupK = BeautifulSoup(pageK)

menuC = soupC.find('div', {'id':'myMenuContainer'})
menuK = soupK.find('div', {'id':'myMenuContainer'})

menuC = menuC.get_text()
menuC = menuC.replace("BreakfastHot", "Breakfast:\nHot")
menuC = menuC.replace("LunchSoup", "\n\n\nLunch: \nSoup")
menuC = menuC.replace("Flip-Side", "\nFlip-Side")
menuC = menuC.replace("Taqueria", "\nTaqueria")
menuC = menuC.replace("Piazza", "\nPiazza")
menuC = menuC.replace("UpperCrust", "\nUpperCrust")
menuC = menuC.replace("DinnerSoup", "\n\n\nDinner: \nSoup")

k = ''
for content in menuK.contents:
     k += str(content)

print menuC
print menuK

textC = open("commons.txt", "w")
textC.write(menuC.encode('utf-8'))
textC.close()



