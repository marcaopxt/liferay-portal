StringBundler sb = new StringBundler();

sb.append(_SQL_COUNT_${entity.alias?upper_case}_WHERE);

<#include "persistence_impl_finder_arrayable_cols.ftl">