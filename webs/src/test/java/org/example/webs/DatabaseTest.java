package org.example.webs;


import org.example.webs.database.Database;
import org.example.webs.database.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class DatabaseTest {

    @Test
    void createTable() {
        Database database = new Database(null);
        assertSame(Result.Status.OK, database.query("create table test1 (INT id, STR name)").getStatus());
        assertSame(Result.Status.OK, database.query("create table test2 (INT id, STR name)").getStatus());

        Result result = database.query("list tables");
        assertSame(Result.Status.OK, result.getStatus());
        assertSame(2, result.getRows().size());
    }

    @Test
    void removeTable() {
        Database database = new Database(null);
        assertSame(Result.Status.OK, database.query("create table test1 (INT id, STR name)").getStatus());
        assertSame(Result.Status.OK, database.query("create table test2 (INT id, STR name)").getStatus());

        Result resultBeforeRemoving = database.query("list tables");
        assertSame(Result.Status.OK, resultBeforeRemoving.getStatus());
        assertSame(2, resultBeforeRemoving.getRows().size());

        assertSame(Result.Status.OK, database.query("remove table test1").getStatus());
        assertSame(Result.Status.OK, database.query("remove table test2").getStatus());

        Result result = database.query("list tables");
        assertSame(Result.Status.OK, result.getStatus());
        assertSame(0, result.getRows().size());
    }

    @Test
    void deleteRows() {
        Database database = new Database(null);
        assertSame(Result.Status.OK, database.query("create table test1 (INT id, STR name)").getStatus());

        assertSame(Result.Status.OK, database.query("insert into test1 (id, name) values(1, KOSTYAAAA697)").getStatus());
        assertSame(1, database.query("select * from test1").getRows().size());


        assertSame(Result.Status.OK, database.query("delete from test1 where id=1").getStatus());
        assertSame(0, database.query("select * from test1").getRows().size());
    }

    @Test
    void money() {
        Database database = new Database(null);
        assertSame(Result.Status.OK, database.query("create table test2 (MONEY money)").getStatus());

        assertSame(Result.Status.FAIL, database.query("insert into test2 (money) values(100as000)").getStatus());
        assertSame(Result.Status.FAIL, database.query("insert into test2 (money) values(assssa100000)").getStatus());
        assertSame(Result.Status.OK, database.query("insert into test2 (money) values(100000.00)").getStatus());
    }

    @Test
    void moneyInv() {
        Database database = new Database(null);
        assertSame(Result.Status.OK, database.query("create table test3 (MONEY_INV inv)").getStatus());

        assertSame(Result.Status.FAIL, database.query("insert into test3 (inv) values($aaasas100000.00;$10,000.00)").getStatus());
        assertSame(Result.Status.FAIL, database.query("insert into test3 (inv) values(100000.asas00;$asaa10,000.00)").getStatus());
        assertSame(Result.Status.OK, database.query("insert into test3 (inv) values(100000.00;10000.00)").getStatus());
    }

    @Test
    void combineOperation() {
        Database database = new Database(null);
        assertSame(Result.Status.OK, database.query("create table white_cats (INT id, STR name)").getStatus());
        assertSame(Result.Status.OK, database.query("create table black_cats (INT id, STR name)").getStatus());

        assertSame(Result.Status.OK, database.query("insert into white_cats  (id, name) values(1, cat1)").getStatus());
        assertSame(Result.Status.OK, database.query("insert into white_cats  (id, name) values(3, cat2)").getStatus());
        assertSame(Result.Status.OK, database.query("insert into white_cats  (id, name) values(4, cat4)").getStatus());
        assertSame(Result.Status.OK, database.query("insert into white_cats  (id, name) values(6, cat8)").getStatus());

        assertSame(Result.Status.OK, database.query("insert into black_cats  (id, name) values(3, cat2)").getStatus());
        assertSame(Result.Status.OK, database.query("insert into black_cats  (id, name) values(4, cat4)").getStatus());
        assertSame(Result.Status.OK, database.query("insert into black_cats  (id, name) values(6, cat15)").getStatus());

        Result result = database.query("COMBINE white_cats WITH black_cats");
        assertSame(Result.Status.OK, result.getStatus());
        assertSame(7, result.getRows().size());

        assertSame(Result.Status.OK, database.query("create table white_cats_2 (INT id, STR name, MONEY price)").getStatus());
        assertSame(Result.Status.OK, database.query("create table black_cats_2 (INT id, STR name, MONEY_INV price_interval)").getStatus());

        result = database.query("COMBINE white_cats_2 WITH black_cats_2");
        assertSame(Result.Status.OK, result.getStatus());
        assertSame(0, result.getRows().size());

        assertSame(Result.Status.OK, database.query("create table white_cats_3 (INT id, STR name, MONEY price)").getStatus());
        assertSame(Result.Status.OK, database.query("create table black_cats_3 (INT id, STR name)").getStatus());

        result = database.query("COMBINE white_cats_3 WITH black_cats_3");
        assertSame(Result.Status.OK, result.getStatus());
        assertSame(0, result.getRows().size());
    }
}
