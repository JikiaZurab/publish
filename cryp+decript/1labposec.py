import copy

f = open('text.txt','r')
cf = open('cleantext.txt','w')
cfp = open('cleantextpart.txt','w')


alphaOne = [["a",0],["b",0],["c",0],["d",0],["e",0],["f",0],["g",0],["h",0],["i",0],["j",0],["k",0],["l",0]
            ,["m",0],["n",0],["o",0],["p",0],["q",0],["r",0],["s",0],["t",0],["u",0],["v",0],["w",0],["x",0],["y",0],["z",0]]
alphaTwo = copy.deepcopy(alphaOne)

n = 5
p = 0

for line in f:
    for i in range(0,len(line)):
        if line[i] == '\n' or line[i] == '.' or line[i] == ' ' or line[i].isalpha():
            if (line[i] == '\n'):
                p+=1
                break
            cf.write(line[i])
            if (p<110):
                cfp.write(line[i])
            if line[i].isalpha():
                for k in range (0,len(alphaOne)):
                    if line[i].lower() == alphaOne[k][0]:
                        alphaOne[k][1] += 1
                        break

f.close()
cf.close()

alphaOne.sort(key=lambda i: i[1], reverse=1)

cfp.close()
cfp = open('cleantextpart.txt','r')
ef = open('encrypttext.txt','w')

for line in cfp:
    for i in range(0,len(line)):
        if not line[i].isalpha():
            ef.write(line[i])
        else:
            w = line[i]
            if chr(ord(w)+n).isalpha():
                w = chr(ord(w)+n)
            else:
                w = chr(ord(w)+n-26)
            ef.write(w)
            for k in range (0,len(alphaTwo)):
                    if w.lower() == alphaTwo[k][0]:
                        alphaTwo[k][1] += 1
                        break
                    
alphaTwo.sort(key=lambda i: i[1], reverse=1)

ef.close()
cfp.close()

ef = open('encrypttext.txt','r')
df = open('decrypttext.txt','w')

for line in ef:
    for i in range(0,len(line)):
        if not line[i].isalpha():
            df.write(line[i])
        else:
            for k in range(0,len(alphaTwo)):
                if line[i].lower() == alphaTwo[k][0]:
                    if line[i].islower():
                        df.write(alphaOne[k][0])
                        #print(line[i],alphaTwo[k][0],alphaOne[k][0])
                    else:
                        df.write(alphaOne[k][0].upper())
                        #print(line[i],alphaTwo[k][0],alphaOne[k][0])
                    break

        
df.close()
ef.close()
print(alphaOne)
print(alphaTwo)

