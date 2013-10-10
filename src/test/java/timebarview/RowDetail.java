package timebarview;

public class RowDetail {
    private String _name;
    private String _description;

    public RowDetail(String name, String description) {
        _name = name;
        _description = description;
    }

    /**
     * @return the _name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name the _name to set
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * @return the _description
     */
    public String getDescription() {
        return _description;
    }

    /**
     * @param description the _description to set
     */
    public void setDescription(String description) {
        _description = description;
    }
}