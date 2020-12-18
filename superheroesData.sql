USE superheroesdb;
INSERT INTO `location` VALUES (1,'Buckingham Palace','Outside Queen\'s Palace','Westminster, London',51.501504,-0.141796),(2,'Leicester Square','In the middle of Square','West End, London',51.510550,-0.130080);
INSERT INTO `organisation` VALUES (1,'Villains Co','Exclusive for the baddest','Deep Underground',"66606660"),(2,'Save The World','Best Superheroes','Charing Cross, London',"704231782"),(3,'Super EU','For everyone with superpowers in EU','Brussels HQ',"07439821300");
INSERT INTO `superhero` VALUES (1,'Spider Man','Blue and red suit','Spider'),(2,'Bat Man','Dark suit','Bat');
INSERT INTO `organisationSuperhero` VALUES (2,1),(3,1),(1,2);
INSERT INTO `sighting` VALUES (1,2,2,'2019-09-12'),(2,1,1,'2019-03-02');
