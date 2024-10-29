package pro.sky.team2.bank_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class InfoDTO {
    @JsonProperty("name")
    String name;

    @JsonProperty("version")
    String version;

    public InfoDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoDTO infoDTO = (InfoDTO) o;
        return Objects.equals(name, infoDTO.name) && Objects.equals(version, infoDTO.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    @Override
    public String toString() {
        return "InfoDTO{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
