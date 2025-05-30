package gs.beSafe.dto;

public class UserResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private Integer roleId;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String nome, String email, Integer roleId) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
