/*
 * This file is generated by jOOQ.
 */
package jooq.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FeatureCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long    id;
    private Integer hash;

    public FeatureCollection() {}

    public FeatureCollection(FeatureCollection value) {
        this.id = value.id;
        this.hash = value.hash;
    }

    public FeatureCollection(
        Long    id,
        Integer hash
    ) {
        this.id = id;
        this.hash = hash;
    }

    /**
     * Getter for <code>public.feature_collection.id</code>.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.feature_collection.id</code>.
     */
    public FeatureCollection setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>public.feature_collection.hash</code>.
     */
    public Integer getHash() {
        return this.hash;
    }

    /**
     * Setter for <code>public.feature_collection.hash</code>.
     */
    public FeatureCollection setHash(Integer hash) {
        this.hash = hash;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FeatureCollection other = (FeatureCollection) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (hash == null) {
            if (other.hash != null)
                return false;
        }
        else if (!hash.equals(other.hash))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.hash == null) ? 0 : this.hash.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FeatureCollection (");

        sb.append(id);
        sb.append(", ").append(hash);

        sb.append(")");
        return sb.toString();
    }
}