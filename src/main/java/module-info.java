module graphvis1 {
    exports graphvis.group30;

    requires fxgraph;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;

    opens graphvis.group30 to javafx.fxml;
}
