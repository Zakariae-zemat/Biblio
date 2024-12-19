import React, {useEffect} from 'react';
import {columns, statusOptions} from "../data/dataPretations.js";
import {
    Button,
    Chip,
    Dropdown,
    DropdownItem,
    DropdownMenu,
    DropdownTrigger,
    Input,
    Pagination, Table, TableBody, TableCell, TableColumn, TableHeader, TableRow, Tooltip,
    User
} from "@nextui-org/react";
import { IoIosCheckbox } from "react-icons/io";
import {MdDelete, MdNotificationsActive} from "react-icons/md";
import {SearchIcon} from "../utils/SearchIcon.jsx";
import {ChevronDownIcon} from "../utils/ChevronDownIcon.jsx";
import {capitalize} from "../utils/utils.js";
import AddBibliothecaire from "../admin/AddBibliothecaire.jsx";
import NavbarLayout from "../NavbarLayout.jsx";
import axios from "../../api/axios.js";
import documents from "../../pages/Documents.jsx";
import useAuth from "../../hook/useAuth.js";
import {PiKeyReturnFill} from "react-icons/pi";
import toast from "react-hot-toast";

const statusColorMap = {
    En_Cour: "success",
    En_Retard: "danger",
};

const INITIAL_VISIBLE_COLUMNS = ["id","nomUser","client","titreDocument", "dateDebut","dateFin", "statuePret", "actions"];

const Pretations = (props) => {
    const {auth} = useAuth()
    const [isChanged,setChaged] = React.useState(false)
    const [pretations,setPretaions] = React.useState([]);
    const [filterValue, setFilterValue] = React.useState("");
    const [selectedKeys, setSelectedKeys] = React.useState(new Set([]));
    const [visibleColumns, setVisibleColumns] = React.useState(new Set(INITIAL_VISIBLE_COLUMNS));
    const [statusFilter, setStatusFilter] = React.useState("all");
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const [sortDescriptor, setSortDescriptor] = React.useState({
        column: "age",
        direction: "ascending",
    });
    const [docs,getDocs] = React.useState([]);

    const [page, setPage] = React.useState(1);

    const pages = Math.ceil(pretations.length / rowsPerPage);

    const hasSearchFilter = Boolean(filterValue);

    const headerColumns = React.useMemo(() => {
        if (visibleColumns === "all") return columns;

        return columns.filter((column) => Array.from(visibleColumns).includes(column.uid));
    }, [visibleColumns]);

    const filteredItems = React.useMemo(() => {
        let filteredUsers = [...pretations];

        if (hasSearchFilter) {
            filteredUsers = filteredUsers.filter((user) =>
                user.titreDocument.toLowerCase().includes(filterValue.toLowerCase()),
            );
        }
        if (statusFilter !== "all" && Array.from(statusFilter).length !== statusOptions.length) {
            filteredUsers = filteredUsers.filter((user) =>
                Array.from(statusFilter).includes(user.statuePret),
            );
        }

        return filteredUsers;
    }, [pretations, filterValue, statusFilter]);
    const items = React.useMemo(() => {
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;

        return filteredItems.slice(start, end);
    }, [page, filteredItems, rowsPerPage]);

    const sortedItems = React.useMemo(() => {
        return [...items].sort((a, b) => {
            const first = a[sortDescriptor.column];
            const second = b[sortDescriptor.column];
            const cmp = first < second ? -1 : first > second ? 1 : 0;

            return sortDescriptor.direction === "descending" ? -cmp : cmp;
        });
    }, [sortDescriptor, items]);

    const retourner = async (user) => {
        console.log(user)
        try {
            const response = await axios.delete('/prets?id='+user.id,{
                headers:{
                    "Authorization": "Bearer " + auth.token,
                }
            })
            //envoer notification aux client
            const res = await axios.post('/notifications/sendFromPret',{
                userId:user.userId,
                message:"votre pretation est terminer" ,
                dateRetour : user.dateFin,
                dateDepart : user.dateDebut,
                bibliothecaireEmail:auth.email,
                documentId : user.documentId
            },{
                headers:{
                    "Authorization": "Bearer " + auth.token
                }
            })
            toast.success("Document Retourner avec Sucees")
            getPretations()
        }catch (e){
            console.log(e);
        }
    }
    const notify = async (user) => {
        console.log(user)
        try {
            //envoer notification aux client
            const res = await axios.post('/notifications/sendFromPret',{
                userId:user.userId,
                message:"votre pretation est  " +  user.statuePret,
                dateRetour : user.dateFin,
                dateDepart : user.dateDebut,
                bibliothecaireEmail:auth.email,
                documentId : user.documentId
            },{
                headers:{
                    "Authorization": "Bearer " + auth.token
                }
            })
            toast.success("Notification Envoyer")
            setChaged(!isChanged)
        }catch (e){
            toast.error("Erreur");
            console.log(e);
        }
    }
    const renderCell = React.useCallback((user, columnKey) => {
        const cellValue = user[columnKey];

        switch (columnKey) {
            case "nomUser":
                return (
                    <User
                        avatarProps={{radius: "full", size: "sm", src: user.imageUrl}}
                        classNames={{
                            description: "text-default-500",
                        }}
                        description={user.email}
                        name={cellValue}
                    >

                        {user.email}
                    </User>
                );
            case "role":
                return (
                    <div className="flex flex-col">
                        <p className="text-bold text-small capitalize">{cellValue}</p>
                        <p className="text-bold text-tiny capitalize text-default-500">{user.team}</p>
                    </div>
                );
            case "statuePret":
                return (
                    <Chip
                        className="capitalize border-none gap-1 text-default-600"
                        color={statusColorMap[user.statuePret]}
                        size="sm"
                        variant="dot"
                    >
                        {cellValue}
                    </Chip>
                );
            case "actions":
                return (
                    <div className="relative text-black flex justify-end items-center gap-2">
                        <Tooltip color="primary" size="sm" content="Retourner Le Document">
                            <div className="cursor-pointer ">
                                <PiKeyReturnFill className="text-blue-500 text-xl" onClick={
                                    ()=>{
                                        retourner(user)
                                    }
                                }/>
                            </div>
                        </Tooltip>
                        <Tooltip color="danger" size="sm" content="Notify">
                            <div className="cursor-pointer ">
                                <MdNotificationsActive className="text-red-500 text-xl" onClick={
                                    ()=>{
                                        notify(user)
                                    }
                                }/>
                            </div>
                        </Tooltip>

                    </div>
                );
            default:
                return cellValue;
        }
    }, []);

    const onRowsPerPageChange = React.useCallback((e) => {
        setRowsPerPage(Number(e.target.value));
        setPage(1);
    }, []);


    const onSearchChange = React.useCallback((value) => {
        if (value) {
            setFilterValue(value);
            setPage(1);
        } else {
            setFilterValue("");
        }
    }, []);

    const topContent = React.useMemo(() => {
        return (
            <div className="flex flex-col text-black gap-4">
                <div className="flex justify-between gap-3 items-end">
                    <Input
                        isClearable
                        classNames={{
                            base: "w-full sm:max-w-[44%]",
                            inputWrapper: "border-1",
                        }}
                        placeholder="Recherche Par Titre Du Document..."
                        size="sm"
                        startContent={<SearchIcon className="text-default-300" />}
                        value={filterValue}
                        variant="bordered"
                        onClear={() => setFilterValue("")}
                        onValueChange={onSearchChange}
                    />
                    <div className="flex text-black gap-3">
                        <Dropdown>
                            <DropdownTrigger className="hidden sm:flex">
                                <Button
                                    endContent={<ChevronDownIcon className="text-small" />}
                                    size="sm"
                                    variant="flat"
                                >
                                    Status
                                </Button>
                            </DropdownTrigger>
                            <DropdownMenu
                                disallowEmptySelection
                                aria-label="Table Columns"
                                closeOnSelect={false}
                                selectedKeys={statusFilter}
                                selectionMode="multiple"
                                className="text-black"
                                onSelectionChange={setStatusFilter}
                            >
                                {statusOptions.map((status) => (
                                    <DropdownItem key={status.uid} className="capitalize">
                                        {capitalize(status.name)}
                                    </DropdownItem>
                                ))}
                            </DropdownMenu>
                        </Dropdown>
                        <Dropdown>
                            <DropdownTrigger className="hidden sm:flex">
                                <Button
                                    endContent={<ChevronDownIcon className="text-small" />}
                                    size="sm"
                                    variant="flat"
                                >
                                    Columns
                                </Button>
                            </DropdownTrigger>
                            <DropdownMenu
                                disallowEmptySelection
                                aria-label="Table Columns"
                                closeOnSelect={false}
                                selectedKeys={visibleColumns}
                                selectionMode="multiple"
                                className="text-black"
                                onSelectionChange={setVisibleColumns}
                            >
                                {columns.map((column) => (
                                    <DropdownItem key={column.uid} className="capitalize">
                                        {capitalize(column.name)}
                                    </DropdownItem>
                                ))}
                            </DropdownMenu>
                        </Dropdown>
                    </div>
                </div>
                <div className="flex text-black justify-between items-center">
                    <span className="text-default-400 text-small">Total {pretations.length} Pretation</span>
                    <label className="flex items-center text-default-400 text-small">
                        Rows per page:
                        <select
                            className="bg-transparent  outline-none text-default-400 text-small"
                            onChange={onRowsPerPageChange}
                        >
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="15">15</option>
                        </select>
                    </label>
                </div>
            </div>
        );
    }, [
        filterValue,
        statusFilter,
        visibleColumns,
        onSearchChange,
        onRowsPerPageChange,
        pretations.length,
        hasSearchFilter,
    ]);

    const bottomContent = React.useMemo(() => {
        return (
            <div className="py-2 px-2 flex justify-center items-center">
                <Pagination
                    showControls
                    classNames={{
                        cursor: "bg-foreground  text-background",
                    }}
                    color="default"
                    isDisabled={hasSearchFilter}
                    page={page}
                    total={pages}
                    variant="light"
                    onChange={setPage}
                />

            </div>
        );
    }, [selectedKeys, items.length, page, pages, hasSearchFilter]);

    const classNames = React.useMemo(
        () => ({
            wrapper: ["max-h-[382px]", "max-w-3xl"],
            th: ["bg-transparent", "text-default-500", "border-b", "border-divider"],
            td: [
                // changing the rows border radius
                // first
                "group-data-[first=true]:first:before:rounded-none",
                "group-data-[first=true]:last:before:rounded-none",
                // middle
                "group-data-[middle=true]:before:rounded-none",
                // last
                "group-data-[last=true]:first:before:rounded-none",
                "group-data-[last=true]:last:before:rounded-none",
            ],
        }),
        [],
    );

    const getPretations = async () => {
        try {
            const response = await axios.get('/prets', {
                headers: {
                    "Authorization": "Bearer " + auth.token
                }
            });
            setPretaions(response.data);

            console.log(response.data)
        }catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        getPretations()
        console.log("hhh")
    },[auth,isChanged]);

    return(
        <div>
            <div>
                <div className="bg-white rounded-md  p-6 text-black">
                    <Table
                        isCompact
                        removeWrapper
                        aria-label="Example table with custom cells, pagination and sorting"
                        bottomContent={bottomContent}
                        bottomContentPlacement="outside"
                        checkboxesProps={{
                            classNames: {
                                wrapper: "after:bg-foreground after:text-background text-background",
                            },
                        }}
                        classNames={classNames}
                        sortDescriptor={sortDescriptor}
                        topContent={topContent}
                        topContentPlacement="outside"
                        onSelectionChange={setSelectedKeys}
                        onSortChange={setSortDescriptor}
                    >
                        <TableHeader columns={headerColumns}>
                            {(column) => (
                                <TableColumn
                                    key={column.uid}
                                    align={column.uid === "actions" ? "center" : "start"}
                                    allowsSorting={column.sortable}
                                >
                                    {column.name}
                                </TableColumn>
                            )}
                        </TableHeader>
                        <TableBody emptyContent={"No users found"} items={sortedItems}>
                            {(item) => (
                                <TableRow key={item.id}>
                                    {(columnKey) => <TableCell>{renderCell(item, columnKey)}</TableCell>}
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </div>
            </div>
        </div>
    )
}

export default Pretations;


