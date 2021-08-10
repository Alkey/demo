/*
 * This file is generated by jOOQ.
 */
package jooq.tables.records;


import jooq.tables.Client;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ClientRecord extends UpdatableRecordImpl<ClientRecord> implements Record4<Long, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.client.id</code>.
     */
    public ClientRecord setId(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.client.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.client.name</code>.
     */
    public ClientRecord setName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.client.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.client.password</code>.
     */
    public ClientRecord setPassword(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.client.password</code>.
     */
    public String getPassword() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.client.role</code>.
     */
    public ClientRecord setRole(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.client.role</code>.
     */
    public String getRole() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Client.CLIENT.ID;
    }

    @Override
    public Field<String> field2() {
        return Client.CLIENT.NAME;
    }

    @Override
    public Field<String> field3() {
        return Client.CLIENT.PASSWORD;
    }

    @Override
    public Field<String> field4() {
        return Client.CLIENT.ROLE;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getPassword();
    }

    @Override
    public String component4() {
        return getRole();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getPassword();
    }

    @Override
    public String value4() {
        return getRole();
    }

    @Override
    public ClientRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public ClientRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public ClientRecord value3(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public ClientRecord value4(String value) {
        setRole(value);
        return this;
    }

    @Override
    public ClientRecord values(Long value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ClientRecord
     */
    public ClientRecord() {
        super(Client.CLIENT);
    }

    /**
     * Create a detached, initialised ClientRecord
     */
    public ClientRecord(Long id, String name, String password, String role) {
        super(Client.CLIENT);

        setId(id);
        setName(name);
        setPassword(password);
        setRole(role);
    }
}
