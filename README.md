# fault_replicate


Each ts-preserve-service instance maintains one global variable preserveNumber.

When there are more than one instance, the preserveNumber is not correct.

The ui-dashboard will detect it if the user preserve two tickets continuously,

since the return preserveNumber is equal or less than the current showed on the page.