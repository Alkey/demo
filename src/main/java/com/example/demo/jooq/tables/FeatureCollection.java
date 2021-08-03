/*
 * This file is generated by jOOQ.
 */
package jooq.tables;


import java.util.Arrays;
import java.util.List;

import jooq.Keys;
import jooq.Public;
import jooq.tables.records.FeatureCollectionRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
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
public class FeatureCollection extends TableImpl<FeatureCollectionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.feature_collection</code>
     */
    public static final FeatureCollection FEATURE_COLLECTION = new FeatureCollection();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FeatureCollectionRecord> getRecordType() {
        return FeatureCollectionRecord.class;
    }

    /**
     * The column <code>public.feature_collection.id</code>.
     */
    public final TableField<FeatureCollectionRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.feature_collection.hash</code>.
     */
    public final TableField<FeatureCollectionRecord, Integer> HASH = createField(DSL.name("hash"), SQLDataType.INTEGER.nullable(false), this, "");

    private FeatureCollection(Name alias, Table<FeatureCollectionRecord> aliased) {
        this(alias, aliased, null);
    }

    private FeatureCollection(Name alias, Table<FeatureCollectionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.feature_collection</code> table reference
     */
    public FeatureCollection(String alias) {
        this(DSL.name(alias), FEATURE_COLLECTION);
    }

    /**
     * Create an aliased <code>public.feature_collection</code> table reference
     */
    public FeatureCollection(Name alias) {
        this(alias, FEATURE_COLLECTION);
    }

    /**
     * Create a <code>public.feature_collection</code> table reference
     */
    public FeatureCollection() {
        this(DSL.name("feature_collection"), null);
    }

    public <O extends Record> FeatureCollection(Table<O> child, ForeignKey<O, FeatureCollectionRecord> key) {
        super(child, key, FEATURE_COLLECTION);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<FeatureCollectionRecord, Long> getIdentity() {
        return (Identity<FeatureCollectionRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<FeatureCollectionRecord> getPrimaryKey() {
        return Keys.FEATURE_COLLECTION_PKEY;
    }

    @Override
    public List<UniqueKey<FeatureCollectionRecord>> getKeys() {
        return Arrays.<UniqueKey<FeatureCollectionRecord>>asList(Keys.FEATURE_COLLECTION_PKEY);
    }

    @Override
    public FeatureCollection as(String alias) {
        return new FeatureCollection(DSL.name(alias), this);
    }

    @Override
    public FeatureCollection as(Name alias) {
        return new FeatureCollection(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FeatureCollection rename(String name) {
        return new FeatureCollection(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FeatureCollection rename(Name name) {
        return new FeatureCollection(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
