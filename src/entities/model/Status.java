package entities.model;

import java.util.Arrays;

public enum Status {

    INATIVO(0),
    ATIVO(1);

    private Integer status;

    Status(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public static Status getTipoStatus(Integer status) {
        return Arrays.stream(Status.values())
                .filter(sts -> sts.getStatus().equals(status))
                .findFirst()
                .get();
    }
}
