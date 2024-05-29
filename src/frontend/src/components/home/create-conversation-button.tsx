export default function CreateConversationWindowOpen({ setVisibility }: { setVisibility: () => void }) {
    return (
        <div className="bg-green-800 w-10 rounded-lg text-white font-bold text-center text-xl ml-3 cursor-pointer hover:bg-green-900" onClick={setVisibility}>
            +
        </div>
    )
}
