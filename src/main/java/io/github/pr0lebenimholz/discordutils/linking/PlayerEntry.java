package io.github.pr0lebenimholz.discordutils.linking;

import java.util.UUID;

public class PlayerEntry {

    private Status status;
    private final UUID mcId;
    private String mcName;
    private final String dcId;
    private String dcName;

    public PlayerEntry(Status status, UUID mcId, String dcId) {
        this(status, mcId, dcId, null, null);
    }
    public PlayerEntry(Status status, UUID mcId, String dcId, String mcName, String dcName) {
        this.status = status;
        this.mcId = mcId;
        this.mcName = mcName;
        this.dcId = dcId;
        this.dcName = dcName;
    }

    public boolean isConfirmed() {
        return this.status == Status.CONFIRMED;
    }

    public boolean isPending() {
        return this.status == Status.PENDING;
    }

    Status getStatus() {
        return this.status;
    }

    public UUID getMcId() {
        return this.mcId;
    }

    public String getMcIdAsString() {
        return this.mcId.toString();
    }

    public String getMcName() {
        return this.mcName;
    }

    public String getDcId() {
        return this.dcId;
    }

    public String getDcName() {
        return this.dcName;
    }

    public void flagAsConfirmed() {
        this.status = Status.CONFIRMED;
    }

    public enum Status {
        PENDING,
        CONFIRMED;
    }
}
