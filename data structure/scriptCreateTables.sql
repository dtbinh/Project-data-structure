DROP TABLE IF EXISTS "main"."arc";

CREATE TABLE "main"."arc" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "distance" INTEGER NOT NULL,
    "origin" INTEGER NOT NULL,
    "end" INTEGER NOT NULL
);

DROP TABLE IF EXISTS "main"."vertex";

CREATE TABLE "vertex" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "name" TEXT NOT NULL
)
