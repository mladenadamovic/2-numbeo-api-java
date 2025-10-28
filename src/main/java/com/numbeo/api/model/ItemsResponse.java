package com.numbeo.api.model;

import java.util.List;

/**
 * Response wrapper for the Numbeo API items endpoint.
 */
public class ItemsResponse {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ItemsResponse{" +
                "items=" + items +
                '}';
    }
}
