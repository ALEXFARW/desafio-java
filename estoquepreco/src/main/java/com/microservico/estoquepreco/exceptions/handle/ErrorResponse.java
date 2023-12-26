package com.microservico.estoquepreco.exceptions.handle;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

import java.time.LocalDateTime;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class ErrorResponse {
    private String mensagem;
    private String reason;
    private LocalDateTime data;
    private int status;
    private String path;

    public ErrorResponse(String mensagem, LocalDateTime data, int status, String path, String reason) {
        this.mensagem = mensagem;
        this.data = data;
        this.status = status;
        this.path = path;
        this.reason = reason;
    }

    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
}
