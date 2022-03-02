/* For In-Memory-DB */
INSERT INTO partypeople.tag_in_memory SELECT * FROM partypeople.tag WHERE (SELECT COUNT(id) FROM partypeople.tag_in_memory) = 0;
INSERT INTO partypeople.room_in_memory SELECT * FROM partypeople.room WHERE (SELECT COUNT(id) FROM partypeople.room_in_memory) = 0;
