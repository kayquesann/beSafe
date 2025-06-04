package gs.dto;public class UserCreateDTO {

    private String nome;

    private String email;

    private String senha;

    private Integer roleId;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String nome, String email, String senha, Integer roleId) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.roleId = roleId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
