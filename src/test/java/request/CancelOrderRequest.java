package request;

public class CancelOrderRequest {
    private Integer track;

    public CancelOrderRequest(Integer track) {
        this.track = track;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return "CancelOrderRequest{" +
                "track=" + track +
                '}';
    }
}
