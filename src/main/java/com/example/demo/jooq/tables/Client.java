/*
 * This file is generated by jOOQ.
 */
package jooq.tables;


import java.util.Arrays;
import java.util.List;

import jooq.Keys;
import jooq.Public;
import jooq.tables.records.ClientRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Client extends TableImpl<ClientRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.client</code>
     */
    public static final Client CLIENT = new Client();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ClientRecord> getRecordType() {
        return ClientRecord.class;
    }

    /**
     * The column <code>public.client.id</code>.
     */
    public final TableField<ClientRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.client.name</code>.
     */
    public final TableField<ClientRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(150).nullable(false), this, "");

    /**
     * The column <code>public.client.password</code>.
     */
    public final TableField<ClientRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.VARCHAR(250).nullable(false), this, "");

    /**
     * The column <code>public.client.role</code>.
     */
    public final TableField<ClientRecord, String> ROLE = createField(DSL.name("role"), SQLDataType.VARCHAR(250).nullable(false), this, "");

    private Client(Name alias, Table<ClientRecord> aliased) {
        this(alias, aliased, null);
    }

    private Client(Name alias, Table<ClientRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.client</code> table reference
     */
    public Client(String alias) {
        this(DSL.name(alias), CLIENT);
    }

    /**
     * Create an aliased <code>public.client</code> table reference
     */
    public Client(Name alias) {
        this(alias, CLIENT);
    }

    /**
     * Create a <code>public.client</code> table reference
     */
    public Client() {
        this(DSL.name("client"), null);
    }

    public <O extends Record> Client(Table<O> child, ForeignKey<O, ClientRecord> key) {
        super(child, key, CLIENT);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<ClientRecord, Long> getIdentity() {
        return (Identity<ClientRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<ClientRecord> getPrimaryKey() {
        return Keys.CLIENT_PKEY;
    }

    @Override
    public List<UniqueKey<ClientRecord>> getKeys() {
        return Arrays.<UniqueKey<ClientRecord>>asList(Keys.CLIENT_PKEY);
    }

    @Override
    public Client as(String alias) {
        return new Client(DSL.name(alias), this);
    }

    @Override
    public Client as(Name alias) {
        return new Client(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Client rename(String name) {
        return new Client(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Client rename(Name name) {
        return new Client(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
