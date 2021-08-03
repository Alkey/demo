/*
 * This file is generated by jOOQ.
 */
package jooq.tables;


import java.util.Arrays;
import java.util.List;

import jooq.Keys;
import jooq.Public;
import jooq.tables.records.PolygonRecord;

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
public class Polygon extends TableImpl<PolygonRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.polygon</code>
     */
    public static final Polygon POLYGON = new Polygon();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PolygonRecord> getRecordType() {
        return PolygonRecord.class;
    }

    /**
     * The column <code>public.polygon.id</code>.
     */
    public final TableField<PolygonRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.polygon.name</code>.
     */
    public final TableField<PolygonRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(250).nullable(false), this, "");

    /**
     * The column <code>public.polygon.geometry</code>.
     */
    public final TableField<PolygonRecord, String> GEOMETRY = createField(DSL.name("geometry"), SQLDataType.VARCHAR(500).nullable(false), this, "");

    /**
     * The column <code>public.polygon.area</code>.
     */
    public final TableField<PolygonRecord, Double> AREA = createField(DSL.name("area"), SQLDataType.DOUBLE.nullable(false), this, "");

    private Polygon(Name alias, Table<PolygonRecord> aliased) {
        this(alias, aliased, null);
    }

    private Polygon(Name alias, Table<PolygonRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.polygon</code> table reference
     */
    public Polygon(String alias) {
        this(DSL.name(alias), POLYGON);
    }

    /**
     * Create an aliased <code>public.polygon</code> table reference
     */
    public Polygon(Name alias) {
        this(alias, POLYGON);
    }

    /**
     * Create a <code>public.polygon</code> table reference
     */
    public Polygon() {
        this(DSL.name("polygon"), null);
    }

    public <O extends Record> Polygon(Table<O> child, ForeignKey<O, PolygonRecord> key) {
        super(child, key, POLYGON);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<PolygonRecord, Long> getIdentity() {
        return (Identity<PolygonRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<PolygonRecord> getPrimaryKey() {
        return Keys.POLYGON_PKEY;
    }

    @Override
    public List<UniqueKey<PolygonRecord>> getKeys() {
        return Arrays.<UniqueKey<PolygonRecord>>asList(Keys.POLYGON_PKEY);
    }

    @Override
    public Polygon as(String alias) {
        return new Polygon(DSL.name(alias), this);
    }

    @Override
    public Polygon as(Name alias) {
        return new Polygon(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Polygon rename(String name) {
        return new Polygon(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Polygon rename(Name name) {
        return new Polygon(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, Double> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}