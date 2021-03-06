create index IX_9E0C1ECE on AppBuilderApp (companyId, active_);
create index IX_4F325E62 on AppBuilderApp (ddmStructureId);
create index IX_319C0B20 on AppBuilderApp (groupId, companyId, ddmStructureId);
create index IX_EC1E021 on AppBuilderApp (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_65D5FAE3 on AppBuilderApp (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_28C98E35 on AppBuilderAppDeployment (appBuilderAppId, type_[$COLUMN_LENGTH:75$]);