package com.eragapati.sub.pubsubsub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboxMessage {
    private String id;
    private String title;
    private String body;
    private String metadata;
}
