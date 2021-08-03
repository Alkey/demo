/*
 * This file is generated by jOOQ.
 */
package jooq.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long   id;
    private String name;
    private String password;
    private String role;

    public Client() {}

    public Client(Client value) {
        this.id = value.id;
        this.name = value.name;
        this.password = value.password;
        this.role = value.role;
    }

    public Client(
        Long   id,
        String name,
        String password,
        String role
    ) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    /**
     * Getter for <code>public.client.id</code>.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.client.id</code>.
     */
    public Client setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>public.client.name</code>.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>public.client.name</code>.
     */
    public Client setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Getter for <code>public.client.password</code>.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter for <code>public.client.password</code>.
     */
    public Client setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Getter for <code>public.client.role</code>.
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Setter for <code>public.client.role</code>.
     */
    public Client setRole(String role) {
        this.role = role;
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
        final Client other = (Client) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        }
        else if (!password.equals(other.password))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        }
        else if (!role.equals(other.role))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
        result = prime * result + ((this.role == null) ? 0 : this.role.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Client (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(password);
        sb.append(", ").append(role);

        sb.append(")");
        return sb.toString();
    }
}