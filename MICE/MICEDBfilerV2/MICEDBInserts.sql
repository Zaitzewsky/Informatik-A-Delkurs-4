INSERT INTO ANSTALLD (AID, NAMN, TELEFON, MAIL)
 VALUES (
1, 
'My Book', 
'019-23123', 
'jon@collins.gg'
);

INSERT INTO ANSTALLD (AID, NAMN, TELEFON, MAIL)
 VALUES (
2, 
'Bo Bullit', 
'019-18113', 
'joan@jatt.gg'
);

INSERT INTO ANSTALLD (AID, NAMN, TELEFON, MAIL)
 VALUES (
3, 
'Gary Glitter', 
'019-18113', 
'gary@glitter.gg'
);

INSERT INTO ANSTALLD (AID, NAMN, TELEFON, MAIL)
 VALUES (
4, 
'Laiza Minally', 
'019-18112', 
'rej@bann.gg'
);

INSERT INTO ANSTALLD (AID, NAMN, TELEFON, MAIL)
 VALUES (
6, 
'Paul Paljett', 
'019-18113', 
'palle@jett.gg'
);


INSERT INTO PROJEKTLEDARE (AID, ANTAL_PROJETLEDAR_UPPDRAG)
 VALUES (
1, 
0
);

INSERT INTO PROJEKTLEDARE (AID, ANTAL_PROJETLEDAR_UPPDRAG)
 VALUES (
3, 
4
);

INSERT INTO PROJEKTLEDARE (AID, ANTAL_PROJETLEDAR_UPPDRAG)
 VALUES (
6, 
3
);


INSERT INTO SPECIALIST (AID)
 VALUES (
1
);

INSERT INTO SPECIALIST (AID)
 VALUES (
2
);

INSERT INTO SPECIALIST (AID)
 VALUES (
3
);

INSERT INTO SPECIALIST (AID)
 VALUES (
4
);

INSERT INTO SPECIALIST (AID)
 VALUES (
6
);

INSERT INTO SPELPROJEKT (SID, BETECKNING, STARTDATUM, RELEASEDATUM, AID)
 VALUES (
1, 
'Sjunde inseglet - the game', 
'2010-03-17', 
'2014-12-01',
1
);

INSERT INTO SPELPROJEKT (SID, BETECKNING, STARTDATUM, RELEASEDATUM, AID)
 VALUES (
2, 
'Playback tournament', 
'2013-03-10', 
'2013-12-27',
1
);

INSERT INTO SPELPROJEKT (SID, BETECKNING, STARTDATUM, RELEASEDATUM, AID)
 VALUES (
3, 
'Teris 2014', 
'2010-03-17', 
'2014-12-01',
6
);

INSERT INTO PLATTFORM (PID, BENAMNING, PRODUCENT, BESKRIVNING)
 VALUES (
1, 
'Wii', 
'Nintendo', 
'The Wii is a home video game console released by Nintendo on November 19'
);

INSERT INTO PLATTFORM (PID, BENAMNING, PRODUCENT, BESKRIVNING)
 VALUES (
2, 
'Wii U', 
'Nintendo', 
'The Wii U is a video game console from Nintendo and the successor to the Wii.[8] The system was released on 

November 18, 2012'
);

INSERT INTO PLATTFORM (PID, BENAMNING, PRODUCENT, BESKRIVNING)
 VALUES (
3, 
'Nintendo DS', 
'Nintendo', 
'The Nintendo DS is a dual-screen handheld game console developed and released by Nintendo. The device went on 

sale in North America on November 21, 2004'
);

INSERT INTO PLATTFORM (PID, BENAMNING, PRODUCENT, BESKRIVNING)
 VALUES (
4, 
'PlayStation Portable', 
'Sony', 
'The PlayStation Portable (officially abbreviated as PSP) is a handheld game console made by Sony.[3] Development 

of the console was announced during E3 2003,[4] '
);

INSERT INTO PLATTFORM (PID, BENAMNING, PRODUCENT, BESKRIVNING)
 VALUES (
5, 
'PlayStation 3', 
'Sony', 
'The PlayStation 3 (PS3) is a home video game console produced by Sony Computer Entertainment. It is the successor 

to PlayStation 2, as part of the PlayStation series'
);

INSERT INTO PLATTFORM (PID, BENAMNING, PRODUCENT, BESKRIVNING)
 VALUES (
6, 
'XBox', 
'Microsoft', 
'The Xbox is a video gaming brand created by Microsoft. It includes a series of video game consoles developed by 

Microsoft, with consoles in the sixth to eighth generations'
);

INSERT INTO PLATTFORM (PID, BENAMNING, PRODUCENT, BESKRIVNING)
 VALUES (
7, 
'XBox 360', 
'Microsoft', 
'The Xbox 360 is a video game console developed by and produced for Microsoft that was released in 2005. The 

successor to the original Xbox, it is the second console in the Xbox series.'
);

INSERT INTO PLATTFORM (PID, BENAMNING, PRODUCENT, BESKRIVNING)
 VALUES (
8, 
'PlayStation 3', 
'Sony', 
'The PlayStation 3 (PS3) is a home video game console produced by Sony Computer Entertainment. It is the successor 

to PlayStation 2, as part of the PlayStation series'
);

INSERT INTO PLATTFORM (PID, BENAMNING, PRODUCENT, BESKRIVNING)
 VALUES (
9, 
'Vectrex', 
'Milton Bradley Company',
'The Vectrex is a vector display-based video game console that was developed by Western Technologies/Smith 

Engineering.[1] It was licensed and distributed first by General Consumer Electronics (GCE), and then by Milton 

Bradley Company'
);

INSERT INTO KOMPETENSDOMAN (KID, BENAMNING, BESKRIVNING) VALUES ('1', '3D-modelleing', 'In 3D computer graphics, 

3D modeling is the process of developing a mathematical representation of any three-dimensional surface of object 

(either inanimate or living) via specialized software. The product is called a 3D model.');
INSERT INTO KOMPETENSDOMAN (KID, BENAMNING, BESKRIVNING) VALUES ('2', 'UV-mappning', 'UV mapping is the 3D 

modeling process of making a 2D image representation of a 3D model.');
INSERT INTO KOMPETENSDOMAN (KID, BENAMNING, BESKRIVNING) VALUES ('3', 'Projektledning', 'Project management is the discipline of planning, organizing, motivating, and controlling resources to achieve specific goals.');
INSERT INTO KOMPETENSDOMAN (KID, BENAMNING, BESKRIVNING) VALUES ('4', 'C-programmering', 'C är ett generellt, imperativt programspråk. Det tillhör familjen Algol-baserade språk[1] och är avsett för strukturerad programmering.');


INSERT INTO ARBETAR_I (AID, SID)
 VALUES (
1, 
1
);

INSERT INTO ARBETAR_I (AID, SID)
 VALUES (
2, 
2
);



INSERT INTO ARBETAR_I (AID, SID)
 VALUES (
2, 
3
);

INSERT INTO ARBETAR_I (AID, SID)
 VALUES (
4, 
3
);


INSERT INTO ARBETAR_I (AID, SID)
 VALUES (
3, 
3
);

INSERT INTO ARBETAR_I (AID, SID)
 VALUES (
6, 
1
);

INSERT INTO ARBETAR_I (AID, SID)
 VALUES (
6, 
2
);

INSERT INTO HAR_KOMPETENS (AID, KID, PID, KOMPETENSNIVA) VALUES ('1', '1', '1', '3');
INSERT INTO HAR_KOMPETENS (AID, KID, PID, KOMPETENSNIVA) VALUES ('1', '2', '1', '3');
INSERT INTO HAR_KOMPETENS (AID, KID, PID, KOMPETENSNIVA) VALUES ('1', '3', '1', '4');
INSERT INTO HAR_KOMPETENS (AID, KID, PID, KOMPETENSNIVA) VALUES ('2', '1', '1', '3');
INSERT INTO HAR_KOMPETENS (AID, KID, PID, KOMPETENSNIVA) VALUES ('2', '2', '1', '4');
INSERT INTO HAR_KOMPETENS (AID, KID, PID, KOMPETENSNIVA) VALUES ('3', '4', '4', '2');
INSERT INTO HAR_KOMPETENS (AID, KID, PID, KOMPETENSNIVA) VALUES ('4', '1', '8', '3');
INSERT INTO HAR_KOMPETENS (AID, KID, PID, KOMPETENSNIVA) VALUES ('6', '2', '6', '5');

INSERT INTO INNEFATTAR (SID, PID) VALUES ('1', '1');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('1', '2');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('2', '3');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('2', '4');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('2', '5');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('2', '6');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('2', '7');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('2', '8');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('2', '9');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('3', '2');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('3', '3');
INSERT INTO INNEFATTAR (SID, PID) VALUES ('3', '1');
