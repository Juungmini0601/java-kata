/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.generated.JJavaKata;
import org.jooq.generated.Keys;
import org.jooq.generated.tables.JProblems.ProblemsPath;
import org.jooq.generated.tables.JTags.TagsPath;
import org.jooq.generated.tables.records.CategoriesRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.19.5",
        "schema version:1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JCategories extends TableImpl<CategoriesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>java_kata.categories</code>
     */
    public static final JCategories CATEGORIES = new JCategories();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CategoriesRecord> getRecordType() {
        return CategoriesRecord.class;
    }

    /**
     * The column <code>java_kata.categories.id</code>.
     */
    public final TableField<CategoriesRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>java_kata.categories.name</code>.
     */
    public final TableField<CategoriesRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>java_kata.categories.description</code>.
     */
    public final TableField<CategoriesRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>java_kata.categories.created_at</code>.
     */
    public final TableField<CategoriesRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(0).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>java_kata.categories.updated_at</code>.
     */
    public final TableField<CategoriesRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(0), this, "");

    private JCategories(Name alias, Table<CategoriesRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JCategories(Name alias, Table<CategoriesRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>java_kata.categories</code> table reference
     */
    public JCategories(String alias) {
        this(DSL.name(alias), CATEGORIES);
    }

    /**
     * Create an aliased <code>java_kata.categories</code> table reference
     */
    public JCategories(Name alias) {
        this(alias, CATEGORIES);
    }

    /**
     * Create a <code>java_kata.categories</code> table reference
     */
    public JCategories() {
        this(DSL.name("categories"), null);
    }

    public <O extends Record> JCategories(Table<O> path, ForeignKey<O, CategoriesRecord> childPath, InverseForeignKey<O, CategoriesRecord> parentPath) {
        super(path, childPath, parentPath, CATEGORIES);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class CategoriesPath extends JCategories implements Path<CategoriesRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> CategoriesPath(Table<O> path, ForeignKey<O, CategoriesRecord> childPath, InverseForeignKey<O, CategoriesRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private CategoriesPath(Name alias, Table<CategoriesRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public CategoriesPath as(String alias) {
            return new CategoriesPath(DSL.name(alias), this);
        }

        @Override
        public CategoriesPath as(Name alias) {
            return new CategoriesPath(alias, this);
        }

        @Override
        public CategoriesPath as(Table<?> alias) {
            return new CategoriesPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JJavaKata.JAVA_KATA;
    }

    @Override
    public Identity<CategoriesRecord, Long> getIdentity() {
        return (Identity<CategoriesRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<CategoriesRecord> getPrimaryKey() {
        return Keys.KEY_CATEGORIES_PRIMARY;
    }

    @Override
    public List<UniqueKey<CategoriesRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_CATEGORIES_NAME);
    }

    private transient ProblemsPath _problems;

    /**
     * Get the implicit to-many join path to the <code>java_kata.problems</code>
     * table
     */
    public ProblemsPath problems() {
        if (_problems == null)
            _problems = new ProblemsPath(this, null, Keys.PROBLEMS_IBFK_1.getInverseKey());

        return _problems;
    }

    private transient TagsPath _tags;

    /**
     * Get the implicit to-many join path to the <code>java_kata.tags</code>
     * table
     */
    public TagsPath tags() {
        if (_tags == null)
            _tags = new TagsPath(this, null, Keys.TAGS_IBFK_1.getInverseKey());

        return _tags;
    }

    @Override
    public JCategories as(String alias) {
        return new JCategories(DSL.name(alias), this);
    }

    @Override
    public JCategories as(Name alias) {
        return new JCategories(alias, this);
    }

    @Override
    public JCategories as(Table<?> alias) {
        return new JCategories(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JCategories rename(String name) {
        return new JCategories(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JCategories rename(Name name) {
        return new JCategories(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JCategories rename(Table<?> name) {
        return new JCategories(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JCategories where(Condition condition) {
        return new JCategories(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JCategories where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JCategories where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JCategories where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JCategories where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JCategories where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JCategories where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JCategories where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JCategories whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JCategories whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
