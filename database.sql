PGDMP     6                    y         
   disk_store    13.2    13.2 0    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16587 
   disk_store    DATABASE     g   CREATE DATABASE disk_store WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
    DROP DATABASE disk_store;
                postgres    false            �            1259    24960    author    TABLE     g   CREATE TABLE public.author (
    id integer NOT NULL,
    full_name character varying(100) NOT NULL
);
    DROP TABLE public.author;
       public         heap    postgres    false            �            1259    24958    authors_id_seq1    SEQUENCE     �   CREATE SEQUENCE public.authors_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.authors_id_seq1;
       public          postgres    false    208            �           0    0    authors_id_seq1    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.authors_id_seq1 OWNED BY public.author.id;
          public          postgres    false    207            �            1259    24909    disk    TABLE     �   CREATE TABLE public.disk (
    id integer NOT NULL,
    title character varying(50) NOT NULL,
    price numeric(10,2) NOT NULL,
    author_id integer NOT NULL,
    cover_image character varying(1024),
    description text
);
    DROP TABLE public.disk;
       public         heap    postgres    false            �            1259    16640 
   disk_genre    TABLE     `   CREATE TABLE public.disk_genre (
    disk_id integer NOT NULL,
    genre_id integer NOT NULL
);
    DROP TABLE public.disk_genre;
       public         heap    postgres    false            �            1259    24907    disks_id_seq    SEQUENCE     �   CREATE SEQUENCE public.disks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.disks_id_seq;
       public          postgres    false    206            �           0    0    disks_id_seq    SEQUENCE OWNED BY     <   ALTER SEQUENCE public.disks_id_seq OWNED BY public.disk.id;
          public          postgres    false    205            �            1259    24988    genre    TABLE     `   CREATE TABLE public.genre (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);
    DROP TABLE public.genre;
       public         heap    postgres    false            �            1259    24986    genres_id_seq    SEQUENCE     �   CREATE SEQUENCE public.genres_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.genres_id_seq;
       public          postgres    false    210            �           0    0    genres_id_seq    SEQUENCE OWNED BY     >   ALTER SEQUENCE public.genres_id_seq OWNED BY public.genre.id;
          public          postgres    false    209            �            1259    24894    orders    TABLE     �   CREATE TABLE public.orders (
    id integer NOT NULL,
    user_id integer NOT NULL,
    disk_id integer NOT NULL,
    price numeric(5,2) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL
);
    DROP TABLE public.orders;
       public         heap    postgres    false            �            1259    24892    orders_id_seq    SEQUENCE     �   CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.orders_id_seq;
       public          postgres    false    204            �           0    0    orders_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;
          public          postgres    false    203            �            1259    24781    users    TABLE     �  CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(32) NOT NULL,
    password character(60) NOT NULL,
    is_admin boolean NOT NULL,
    email character varying(254),
    first_name character varying(35),
    last_name character varying(35),
    city character varying(45),
    address character varying(120),
    postal_index character varying(45),
    loan numeric,
    is_banned boolean
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    24779    user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.user_id_seq;
       public          postgres    false    202            �           0    0    user_id_seq    SEQUENCE OWNED BY     <   ALTER SEQUENCE public.user_id_seq OWNED BY public.users.id;
          public          postgres    false    201            C           2604    24963 	   author id    DEFAULT     h   ALTER TABLE ONLY public.author ALTER COLUMN id SET DEFAULT nextval('public.authors_id_seq1'::regclass);
 8   ALTER TABLE public.author ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    208    207    208            B           2604    24912    disk id    DEFAULT     c   ALTER TABLE ONLY public.disk ALTER COLUMN id SET DEFAULT nextval('public.disks_id_seq'::regclass);
 6   ALTER TABLE public.disk ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    206    205    206            D           2604    24991    genre id    DEFAULT     e   ALTER TABLE ONLY public.genre ALTER COLUMN id SET DEFAULT nextval('public.genres_id_seq'::regclass);
 7   ALTER TABLE public.genre ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    209    210    210            A           2604    24897 	   orders id    DEFAULT     f   ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);
 8   ALTER TABLE public.orders ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    204    203    204            @           2604    24784    users id    DEFAULT     c   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    201    202    202            �          0    24960    author 
   TABLE DATA           /   COPY public.author (id, full_name) FROM stdin;
    public          postgres    false    208   �3       �          0    24909    disk 
   TABLE DATA           U   COPY public.disk (id, title, price, author_id, cover_image, description) FROM stdin;
    public          postgres    false    206   j4       �          0    16640 
   disk_genre 
   TABLE DATA           7   COPY public.disk_genre (disk_id, genre_id) FROM stdin;
    public          postgres    false    200   zB       �          0    24988    genre 
   TABLE DATA           )   COPY public.genre (id, name) FROM stdin;
    public          postgres    false    210   �B       �          0    24894    orders 
   TABLE DATA           S   COPY public.orders (id, user_id, disk_id, price, start_date, end_date) FROM stdin;
    public          postgres    false    204   C       �          0    24781    users 
   TABLE DATA           �   COPY public.users (id, username, password, is_admin, email, first_name, last_name, city, address, postal_index, loan, is_banned) FROM stdin;
    public          postgres    false    202   mC       �           0    0    authors_id_seq1    SEQUENCE SET     >   SELECT pg_catalog.setval('public.authors_id_seq1', 11, true);
          public          postgres    false    207            �           0    0    disks_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.disks_id_seq', 20, true);
          public          postgres    false    205            �           0    0    genres_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.genres_id_seq', 8, true);
          public          postgres    false    209            �           0    0    orders_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.orders_id_seq', 10, true);
          public          postgres    false    203            �           0    0    user_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.user_id_seq', 19, true);
          public          postgres    false    201            P           2606    24968    author authors_pkey1 
   CONSTRAINT     R   ALTER TABLE ONLY public.author
    ADD CONSTRAINT authors_pkey1 PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.author DROP CONSTRAINT authors_pkey1;
       public            postgres    false    208            N           2606    24917    disk disks_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY public.disk
    ADD CONSTRAINT disks_pkey PRIMARY KEY (id);
 9   ALTER TABLE ONLY public.disk DROP CONSTRAINT disks_pkey;
       public            postgres    false    206            F           2606    24871    users email_uniq 
   CONSTRAINT     L   ALTER TABLE ONLY public.users
    ADD CONSTRAINT email_uniq UNIQUE (email);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT email_uniq;
       public            postgres    false    202            R           2606    24993    genre genres_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genres_pkey PRIMARY KEY (id);
 ;   ALTER TABLE ONLY public.genre DROP CONSTRAINT genres_pkey;
       public            postgres    false    210            L           2606    24902    orders orders_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public            postgres    false    204            H           2606    24789    users user_pkey1 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_pkey1 PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT user_pkey1;
       public            postgres    false    202            J           2606    24869    users username_uniq 
   CONSTRAINT     R   ALTER TABLE ONLY public.users
    ADD CONSTRAINT username_uniq UNIQUE (username);
 =   ALTER TABLE ONLY public.users DROP CONSTRAINT username_uniq;
       public            postgres    false    202            W           2606    24973    disk disk_author_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.disk
    ADD CONSTRAINT disk_author_fkey FOREIGN KEY (author_id) REFERENCES public.author(id) NOT VALID;
 ?   ALTER TABLE ONLY public.disk DROP CONSTRAINT disk_author_fkey;
       public          postgres    false    2896    208    206            S           2606    24948    disk_genre disk_fkey    FK CONSTRAINT     |   ALTER TABLE ONLY public.disk_genre
    ADD CONSTRAINT disk_fkey FOREIGN KEY (disk_id) REFERENCES public.disk(id) NOT VALID;
 >   ALTER TABLE ONLY public.disk_genre DROP CONSTRAINT disk_fkey;
       public          postgres    false    206    200    2894            T           2606    24994    disk_genre genre_fkey    FK CONSTRAINT        ALTER TABLE ONLY public.disk_genre
    ADD CONSTRAINT genre_fkey FOREIGN KEY (genre_id) REFERENCES public.genre(id) NOT VALID;
 ?   ALTER TABLE ONLY public.disk_genre DROP CONSTRAINT genre_fkey;
       public          postgres    false    200    210    2898            V           2606    24928    orders order_disk_fkey    FK CONSTRAINT     ~   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT order_disk_fkey FOREIGN KEY (disk_id) REFERENCES public.disk(id) NOT VALID;
 @   ALTER TABLE ONLY public.orders DROP CONSTRAINT order_disk_fkey;
       public          postgres    false    2894    206    204            U           2606    24918    orders order_user_fkey    FK CONSTRAINT        ALTER TABLE ONLY public.orders
    ADD CONSTRAINT order_user_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;
 @   ALTER TABLE ONLY public.orders DROP CONSTRAINT order_user_fkey;
       public          postgres    false    204    2888    202            �   �   x�3�.IMI�2��,����K-�,�2�0�¾��]�}�G���+.6_l���ʯTp�,�,������I���K�Hu�KO��24����Vp�ɯL�24�0h̆�.l�{/6^�ua��~���6콰�+F��� �A      �      x��YmS��,~�6����zC�4�c;4�K��֝|YI+���D�	��R0;�x�Fp���|�`�$��`�/����ܻ���'�t�ڽ����<�9k�#W�>��c;��#�ȵF�V7+�:Sn=:�V���h�:�'�ɒ�ȹ�/R�T:�I��d�P�8q7�����_�o�����_���Έ����u�����~��ܧ;jɶ�B"jy_{M�÷z�^�_�'�����{=�x��LҝO�=��h�+ށ���&]Y�^ѭ��!_���g��-���U�e��[���ۿC����~ M|��ɖ>X�dY�K1Mߗ��6�}�i�B��������8D�kZo�Qk�����O���[�2�Eg�v��;���
ٹL�	���Yz꾷Og]�/��{����':�S���jU6����5�"�<=],����Ȥ]:P�����c�H��$dI-_�6>/�N!Cf�bى�_�������}t����T��\��6�D��y�2��)ٻcy�t����u8�tס3����[/�߱�$���v���עe�]��22d�KWΏ]8k�I��뱋�8N
)١������u�`�Ҙ�A7�rb��)w)2MJ73��x�.�7R�X���5�,�+S�k�S�rg[d�0��r�m�c~��ް(��x�>��s�rN{NR�7dg"�W�Co����ۢ�G�#6�!�����\}A��-}zI���::�Jn&S�$��Cj4Y-��R)�p�}��W9�o#U�(^��+]]��;t����1(w�*t�SN4��T�_�ο�^�e��+gǣ�rj.hvĭqΑ�Q\Ԣ����V�* �Q�}�`��(�"�-.X�w959�`�J�&e�mF�vŎ}�	}s�bO�����嶅T`3��It��.�x�R��g`�ܧ�����-+�s��h���0Ŀ	`ù<��� �C>����N�G_.�Kl��y�#���
|v��ZD�թ,@���9!�A���ED��u��i{���-�ŉPu8�]}e
0�wq����(���_)tWꜿ�X��;����Z��\�Ŝ�M�=��Z�̦o�U�
�qN �@-�'"���aD˹?��4v?�H ���W�&#M[�d������iG��~�ND��@۞~�� ���y�) ;���&��~Ld�l&���;���N�Υ�L>�)�r�����������v�����:x�l;������C����P���+���XtW�	ǀ�^g�+dI/̤
���m�EJ��6���5:���D��C��t`D�'�Sǚ��O������%4����O	�x?�C�1����"�,��?M��R�Dg�M��r!F�6��n79<�z�����j��L~���.�������`)��`{�;���Wx���1�+Vf���y7﷯���mG��r��i�d��@p%����r!j'2ъۈ�+9���Kc~8s���x�\�O����ދ��\.mۙd.�J����M!YfZNc-�p�(S���Α�C������L�W�hy�� om�]� ���v.�5�JQ���͋>0���.��W�ؠ�z�I�xA�~T�A�o�mQ5�4C�]�1T	��;\��u���>}����k�DfI���I�!�13�>����%�Ԯ���)spK~��CN��P�����m G�e�m����r�Zs	�x�ڃ-\�*2�V�U"
HJ����4����ɞ}}S��mR�`����f��hjAw�� 7����/�)$��&�Ȯ~�j�Z���{�=d�F&.���8q�*)����b'��Y����ܖuN�-��B*�͖~F_j��H��JE�d���T��_7s����qh-��u%�(��d�R�/:���a���?��{f�n"�]v���`�4��uX�8�S[��f?�� ~����%M��©�f���̆Z��M���B]_���^���ak����3gC]Ҧ �
�*[��.s�1�:cEg֩4B��
���@�/!��pB�!~^SK~<vud<���3ڡ��~P�ė�AXNz��PE<c=��X��ԯ9��������%k��x����ECz�;�?c�
��#45��WZ��Cv*rf�Zw�"I.�젌��rE��s�rř���X�D�$}(�R%;Q($rI'_H�3N)�'���n6+�t_�W�	Z�u���Q6/B?6�X
ÝFl�.X�����r��Q���ceFDY�T.6e����ħ7��z*.�]h�P��U*gQ�O�%�΀�n���w��u���t
��ŭH�.���͍�;g�pT����B� �b �-���47�����n֠����ِoѿ�QZ��ЈtN���i=KU��nЖ�� ����+��g2�3����m(��-]��j�C�^͠{<���|�^.��]�m�[`)N�W^���������?�a�b<q?P�|t��E�P�v�%�I��_��h�H*����qje�G�D���c���\2K$Sn6V�"�����]����@Z�BE��o(m#�;� N�7���d�2���6�@ۅ�ˢ�/�`���ف c���Th�67[�rŃa^f4XfX�t�^`�%5��jm�p^S��8nO�#oT�$Ѻ�p��^;�5��Ȥ�Ǫ'���;'i��j���H�:��uԒ�(�m��7�2�ܔ��I�b�̬^Il�N\Z�Q%"[����`����X��g�]�.�W��&�>a�O�����ߤN�(��ϗ�*��A�6���pD�C���/D�<m�`���B�,�}�3>W���T4��7vp+��;y{`j�C�uN͌!;�r͵Ɲ�ik� ƪ��]�P�V"�F< �Yw֭�>:7��6R�Vn����T�b=����X<#�)�T��a���0/�6�9��d���M�-[�J{4i"�2�\� `��՛�kXG��P��۝���:Ÿ;�����k*g:��cnÞ�2���i�����=}�$
�j�j����s���۠=yf~���]=��f̌]w��}@�O>�7��M�qLZ���j.��L��Wu�5י�U�k���$�)�TAr~<_1��Jy�5@���<?i�/OYeybrz���Պ�` �'u˩��Y}�*֙�Lu~κZ��>*O�޼?����7k&�-̔ku\J�#�y�{?��}D�q�?nR�/>�ӿ{j�򦁦�λ�|��.�G���c���B�N��R�T(��[�АM�x��a�)�-u���p2��������7ʥ�j�$��&��ׯ�8�xZ�5�/�	��{NX��^�
TC=Pb�x��	!���������!^/,�ٜ�ÉD���R�/�xX�+R�/�.�j0YOA���-ݽ`��|I�J�\��U��Ő���@憐�K�L*y��C�2�0hH&��� @���u��Z�M����ݣw SVT�t�q�ڀ��T��x�Q^��F�؆��lt��[��w0��8�cA4OR��S�,��{�&huM$�9�'~%�HN��cuT�ѡ�����      �   B   x�%��	�0�޾aJ�s�K�����7�r�R΋R�E���5��B7z���&�������      �   '   x�3�J,�2��/�2��O���(������� y�N      �   Z   x�m���0�7�Ȧ-Mv��sP�~"!��	�BRt@�F40�Ŏѻ̔�7g��L�-����yJG�!���Hi%�(��+b��tU}np      �      x���͎�P�ח�`�R˽-�a"��c`:@LL�"� m�jt6&N������yÌ_��p�F����$.\ܓ۞�~��2�ئ6%^�0I�����������\kjr%cs#��8i����ޘ�����NT�6u�Jz~F�+\g|D�Dx+�)|��{W���=�%|³�ϰ$��=����=��'���+���
_��
�w�	��2�P��Q�G�q�,X9N��i�6��'{��rüҎZ�(ّd��yf_l�'�9���:����_}hx�`k$^����?��qx�skl���L��ֶݴے�f��5�yQ�L��N����6ߘ�rB�XKK\�XD���3V�J��	��^��|E��	|��v>�#�)lP��}��^&���c"��(G}�=�VC��b���L�d�@E1!B�fwcem!��f�QJ%y_?LF:��rIR��z#*��7���w���E�������q��[�p�����e��^��&�
��|`�
=�B��/ÇJ�     