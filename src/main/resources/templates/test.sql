CREATE OR REPLACE FUNCTION get_type_category_tree_recursive(parent_category_id varchar, lang varchar)
    returns varchar
    language plpgsql
AS
$$
declare
result_json varchar;
begin

select '[' || string_agg(temp_table.body, ',') || ']'
into result_json
from (SELECT json_build_object('id', ct.id,
                               'name', CASE lang
                                           WHEN 'uz' THEN ct.name_uz
                                           WHEN 'en' THEN ct.name_en
                                           ELSE ct.name_ru
                                   END,
                               'parentId', parent_id,
                               'photoId', photo_id,
                               'child', get_type_category_tree_recursive(ct.id, lang)):: text as body
      from category as ct
      where ct.parent_id = parent_category_id
        and ct.visible = true) as temp_table;

return result_json;
end
$$;
