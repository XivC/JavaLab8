
file = open("application_ru_RU.properties", "r", encoding = "windows-1251").readlines()
buffer = open("buffer.txt", "a")
keys = []
for line in file:
    keys.append(line.split("=")[0])
    buffer.write(line.split("=")[1])
    
