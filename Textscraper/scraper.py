'''
Created on Nov 1, 2016

@author: Kyle
'''
from lxml import html
import requests

commonsPage = requests.get('https://calvin.edu/directory/places/commons-dining-hall')
commonsTree = html.fromstring(commonsPage.content)

menu = commonsTree.xpath('//div[@id="Breakfast"]/text()')

print( 'Thing:', menu)