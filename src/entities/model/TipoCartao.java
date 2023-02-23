package entities.model;

public enum TipoCartao {
    DEBITO(1), CREDITO(2);

    private int value;

    TipoCartao(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TipoCartao getTipoCartao(int value) {
        for(TipoCartao tp : TipoCartao.values()) {
            if(tp.value == value)
                return tp;
        }
        return null;
    }
}
