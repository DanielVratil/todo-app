export type TodoEntry = {
    id: number,
    label: string,
    status: 'TODO'|'DONE',
    doneAt?: string,
    createdAt?: string,
}