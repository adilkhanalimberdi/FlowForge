import type {Card} from "../../types/Card.ts";
import CardComponent from "./CardComponent";
import {type Dispatch, type SetStateAction, useState} from "react";


interface Props {
    card: Card;
    setCanSave: Dispatch<SetStateAction<boolean>>;
    saving: boolean;
}


function WorkspacePage({ card, setCanSave, saving }: Props) {
    const [after, setAfter] = useState(structuredClone(card));

    return (
        <div className={"w-[350px] h-[400px] rounded-2xl mb-4 drop-shadow-xl hover:drop-shadow-2xl shadow-black hover:cursor-pointer " +
            ((card.status) === "NOT_STARTED" ? "bg-blue-100" : ((card.status) === "IN_PROGRESS" ? "bg-yellow-100" : "bg-green-100"))}>

            <CardComponent before={card} after={after} setCanSave={setCanSave} saving={saving} setAfter={setAfter} />

        </div>
    )

}

export default WorkspacePage;