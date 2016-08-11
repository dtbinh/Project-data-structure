DROP TABLE IF EXISTS "main"."vertex";
DROP TABLE IF EXISTS "main"."arc";

CREATE TABLE "main"."vertex" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "name" TEXT NOT NULL,
    "x" INTEGER NOT NULL,
    "y" INTEGER NOT NULL
);

CREATE TABLE "main"."arc" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "distance" INTEGER NOT NULL,
    "origin" INTEGER NOT NULL,
    "end" INTEGER NOT NULL,
    FOREIGN KEY ("origin") REFERENCES "vertex" ("id"),
    FOREIGN KEY ("end") REFERENCES "vertex" ("id")
);

INSERT INTO "main"."vertex" ("name", "x", "y") VALUES ('A', 100, 150);
INSERT INTO "main"."vertex" ("name", "x", "y") VALUES ('B', 200, 75);
INSERT INTO "main"."vertex" ("name", "x", "y") VALUES ('C', 450, 100);
INSERT INTO "main"."vertex" ("name", "x", "y") VALUES ('D', 300, 300);
INSERT INTO "main"."vertex" ("name", "x", "y") VALUES ('E', 25, 175);
INSERT INTO "main"."vertex" ("name", "x", "y") VALUES ('F', 400, 250);

INSERT INTO "main"."arc" ("distance", "origin", "end") VALUES(25, 1, 2);
INSERT INTO "main"."arc" ("distance", "origin", "end") VALUES(10, 1, 4);
INSERT INTO "main"."arc" ("distance", "origin", "end") VALUES(5, 3, 1);
INSERT INTO "main"."arc" ("distance", "origin", "end") VALUES(15, 6, 5);
INSERT INTO "main"."arc" ("distance", "origin", "end") VALUES(20, 5, 2);
INSERT INTO "main"."arc" ("distance", "origin", "end") VALUES(30, 3, 2);